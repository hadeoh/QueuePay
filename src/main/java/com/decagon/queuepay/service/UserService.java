package com.decagon.queuepay.service;

import com.decagon.queuepay.exception.EmailException;
import com.decagon.queuepay.models.user.EmailVerificationStatus;
import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.payload.LoginRequest;
import com.decagon.queuepay.models.user.Role;
import com.decagon.queuepay.payload.SignupRequest;
import com.decagon.queuepay.repositories.UserRepository;
import com.decagon.queuepay.response.JwtResponse;
import com.decagon.queuepay.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;

@Service
public class UserService {
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private EmailSenderService emailSenderService;

    @Autowired
    public UserService(UserRepository userRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    public void registration(@Valid SignupRequest signupRequest) throws Exception {
        boolean existing = userRepository.existsByUsername(signupRequest.getUsername());
        if (existing) {
            throw new EmailException("This email already exists!");
        }
        User user = new User();

        String token = jwtProvider.createToken(signupRequest.getUsername(), user.getRoles());

        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUsername(signupRequest.getUsername());
        user.setFullName(signupRequest.getFullName());
        user.setRoles(Collections.singletonList(Role.ROLE_CLIENT));
        user.setEmailVerificationToken(token);
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(signupRequest.getUsername());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("testing-70a156@inbox.mailtrap.io");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:3000/confirm-account?token=" + token);

        emailSenderService.sendEmail(mailMessage);
    }

    public void verifyRegistration(String token) throws Exception {
        User user = userRepository.findByEmailVerificationToken(token);
        if (user == null) {
            throw new Exception("Unable to verify that you registered here");
        }
        user.setEmailVerificationStatus(EmailVerificationStatus.VERIFIED);
        userRepository.save(user);
    }

    public ResponseEntity<?> authenticate(LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User myUserDetails = (User) authentication.getPrincipal();
        String token = jwtProvider.createToken(username, loginRequest.getRoles());

        return ResponseEntity.ok(new JwtResponse(token, myUserDetails.getId(), myUserDetails.getUsername(), myUserDetails.getFullName().toUpperCase(), myUserDetails.getPhoneNumber()));
    }


}
