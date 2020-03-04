package com.decagon.queuepay.payload;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDto {

    @NotNull
    @NotBlank(message = "full name required")
    private String fullName;

    @NotNull
    @NotBlank
    private String phoneNumber;

    @NotNull
    @NotBlank
    @JsonIgnoreProperties
    @Size(min = 6)
    private String password;

    @Email
    @NotEmpty
    private String email;

    private List<String> role;
}
