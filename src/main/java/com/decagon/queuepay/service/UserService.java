package com.decagon.queuepay.service;

import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.payload.LoginRequest;
import com.decagon.queuepay.payload.MyUserDetails;
import com.decagon.queuepay.payload.UserRegistrationDto;
import com.decagon.queuepay.repositories.UserRepository;
import com.decagon.queuepay.response.JwtResponse;
import com.decagon.queuepay.response.Message;
import com.decagon.queuepay.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private MyUserDetailsService myUserDetailsService;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private EmailSenderService emailSenderService;

    @Autowired
    public UserService(UserRepository userRepository, MyUserDetailsService myUserDetailsService, JwtProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    public ResponseEntity<?> registration(@Valid UserRegistrationDto userDto) {
        boolean existing = userRepository.existsByEmail(userDto.getEmail());
        if (existing) {
            return ResponseEntity.badRequest().body(new Message("message : This email already exists!"));
        }
        String token = jwtProvider.generateToken(myUserDetailsService.loadUserByUsername(userDto.getEmail()));
        User user = new User();
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        User newUser = userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userDto.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("olayodepossib@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8082/confirm-account?token=" + token);

        emailSenderService.sendEmail(mailMessage);
        return ResponseEntity.ok(new Message("Registration successfull. Go to your email and confirm your account"));
    }

    public JwtResponse authenticate(LoginRequest loginRequest) {
        System.out.println("hi");
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);
        String token = jwtProvider.generateToken(userDetails);

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        List<String> roles = myUserDetails.getAuthorities().stream()
                .map(role -> role.getAuthority()).collect(Collectors.toList());

        return new JwtResponse(token,
                myUserDetails.getId(),
                myUserDetails.getEmail(),
                roles);
    }
}
