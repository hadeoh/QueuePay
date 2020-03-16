package com.decagon.queuepay.security;

import com.decagon.queuepay.models.user.Role;
import com.decagon.queuepay.service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtProvider {

    @Value("${queuepay.jwtSecretKey}")
    private String secretKey;

    @Value("${queuepay.jwtExpirationMs}")
    private Long validity;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles);
        Date currentDate = new Date();
        Date validityTime = new Date(currentDate.getTime() + validity);

        return Jwts.builder().setClaims(claims).setIssuedAt(currentDate).setExpiration(validityTime).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Object getRoles(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("auth");
    }

    public String resolveToken(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        boolean isValid = false;
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            isValid = true;
        }catch (JwtException | IllegalArgumentException ex){
            ex.getMessage();
        }
        return isValid;
    }

}
