package com.decagon.queuepay.controller;

import com.decagon.queuepay.payload.ForgetPasswordRequest;
import com.decagon.queuepay.payload.LoginRequest;
import com.decagon.queuepay.payload.SignupRequest;
import com.decagon.queuepay.response.Message;
import com.decagon.queuepay.exception.Response;
import com.decagon.queuepay.service.MapValidationErrorService;
import com.decagon.queuepay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    private UserService userService;
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public AuthenticationController(UserService userService, MapValidationErrorService mapValidationErrorService) {
        this.userService = userService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest, BindingResult bindingResult) throws Exception {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null){
            return errorMap;
        }
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
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) throws Exception {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null){
            return errorMap;
        }
       return userService.authenticate(loginRequest);
    }

}
