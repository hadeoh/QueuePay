package com.decagon.queuepay.controller;

import com.decagon.queuepay.payload.LoginRequest;
import com.decagon.queuepay.payload.UserRegistrationDto;
import com.decagon.queuepay.response.JwtResponse;
import com.decagon.queuepay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRegistrationDto userDto){
        System.out.println("AM HERE AT SING UP");
        return userService.registration(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        System.out.println(">>>>>>>>>>>>>>>>>");
        JwtResponse response =  userService.authenticate(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
