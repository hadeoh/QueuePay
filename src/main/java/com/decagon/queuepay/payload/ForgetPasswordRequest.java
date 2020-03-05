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
public class ForgetPasswordRequest {
    @NotBlank(message = "Please provide your email")
    private String Email;

    private List<Role> roles;
}
