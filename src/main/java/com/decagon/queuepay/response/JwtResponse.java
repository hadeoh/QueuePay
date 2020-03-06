package com.decagon.queuepay.response;

import com.decagon.queuepay.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse {

    private String token;
    private Integer id;
    private String type = "Bearer";
    private String email;

    public JwtResponse(String token, Integer id, String email) {
        this.token = token;
        this.id = id;
        this.email = email;
    }

}
