package com.decagon.queuepay.response;

import com.decagon.queuepay.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
public class JwtResponse {

    private String token;
    private Integer id;
    private String type = "Bearer";
    private String email;
    private String fullName;
    private String phoneNumber;

    public JwtResponse(String token, Integer id, String email, String fullName, String phoneNumber) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

}
