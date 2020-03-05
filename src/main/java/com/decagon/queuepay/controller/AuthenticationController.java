package com.decagon.queuepay.controller;

import com.decagon.queuepay.payload.LoginRequest;
import com.decagon.queuepay.payload.SignupRequest;
import com.decagon.queuepay.response.Message;
import com.decagon.queuepay.exception.Response;
import com.decagon.queuepay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest) throws Exception {
        userService.registration(signupRequest);
        return ResponseEntity.ok(new Message("Registration successful!"));
    }

    @PatchMapping("verifyEmail/{token}")
    public ResponseEntity<Response<String>> verifyRegistration(@PathVariable String token) throws Exception {
        userService.verifyRegistration(token);
        Response<String> response = new Response<>(HttpStatus.ACCEPTED);
        response.setMessage("You are now a verified user");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        ResponseEntity<?> response =  userService.authenticate(loginRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
