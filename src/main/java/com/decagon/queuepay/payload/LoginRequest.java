package com.decagon.queuepay.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {

    @NotBlank(message = "Please provide your email")
    private String email;

    @NotBlank(message = "Please provide your password")
    private String password;
}
