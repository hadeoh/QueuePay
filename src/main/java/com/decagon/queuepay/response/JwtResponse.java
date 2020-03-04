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
    private UUID id;
    private String type = "Bearer";
    private String email;
    private Object roles;

    public JwtResponse(String token, UUID id, String email, Object roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

}
