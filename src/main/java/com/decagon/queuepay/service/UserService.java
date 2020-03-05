package com.decagon.queuepay.service;

import com.decagon.queuepay.models.user.Role;
import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.payload.ForgetPasswordRequest;
import com.decagon.queuepay.payload.MyUserDetails;
import com.decagon.queuepay.payload.LoginRequest;
import com.decagon.queuepay.payload.SignupRequest;
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
import java.util.Arrays;
import java.util.Collections;
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

    public ResponseEntity<?> registration(@Valid SignupRequest signupRequest) {
        boolean existing = userRepository.existsByEmail(signupRequest.getEmail());
        if (existing) {
            return ResponseEntity.badRequest().body(new Message("This email already exists!"));
        }
        User user = new User();

        String token = jwtProvider.createToken(signupRequest.getEmail(), user.getRoles());

        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setFullName(signupRequest.getFullName());
        user.setRoles(Collections.singletonList(Role.ROLE_CLIENT));
        user.setEmailVerificationToken(token);
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(signupRequest.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("testing-70a156@inbox.mailtrap.io");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8082/confirm-account?token=" + token);

        emailSenderService.sendEmail(mailMessage);
        return ResponseEntity.ok(new Message("Registration Successful. Go to your email and confirm your account"));
    }

    public ResponseEntity<?> authenticate(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        String token = jwtProvider.createToken(email, loginRequest.getRoles());

//        return ResponseEntity.ok(new JwtResponse(token,
//                myUserDetails.getId(),
//                myUserDetails.getEmail(),
//                strRoles));
        return ResponseEntity.ok(new Message("Login Successful."));
    }


}
