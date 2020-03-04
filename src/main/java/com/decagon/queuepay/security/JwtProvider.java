package com.decagon.queuepay.security;

import com.decagon.queuepay.service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Component
public class JwtProvider {

    @Value("${queuepay.jwtSecretKey}")
    private String secretKey;

    @Value("${queuepay.jwtExpirationMs}")
    private Long validity;

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public JwtProvider(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email, List<GrantedAuthority> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
        Date currentDate = new Date();
        Date validityTime = new Date(currentDate.getTime() + validity);

        return Jwts.builder().setClaims(claims).setIssuedAt(currentDate).setExpiration(validityTime).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
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

    public String generateToken(UserDetails userDetails){
        List<GrantedAuthority> roles = new ArrayList<>();
        return createToken(userDetails.getUsername(), roles);
    }
}
