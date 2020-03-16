package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {

    @NotBlank(message = "Please provide your email")
    private String username;

    @NotBlank(message = "Please provide your password")
    private String password;

    private List<Role> roles;
}
