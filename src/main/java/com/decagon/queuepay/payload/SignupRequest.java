package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupRequest {

    @NotBlank(message = "Please name should not be empty")
    private String fullName;

    @NotBlank(message = "Please phone number should not be empty")
    private String phoneNumber;

    @NotBlank(message = "Please email should not be empty")
    private String email;

    @NotBlank(message = "Please password should not be empty")
    @Size(min = 6, max = 16, message = "Please password should be between 6 to 16 characters long")
    private String password;

}
