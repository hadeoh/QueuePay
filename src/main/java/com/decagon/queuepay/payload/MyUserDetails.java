package com.decagon.queuepay.payload;

import com.decagon.queuepay.models.user.Role;
import com.decagon.queuepay.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private Integer id;
    private String email;
    private String phoneNumber;
    private String fullName;

    @JsonIgnore
    private String password;

    private List<Role> authorities;

    public MyUserDetails(Integer id, String email, String phoneNumber, String fullName, String password,
                         List<Role> authorities) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
    }

    public static MyUserDetails build(User user){
        List<Role> authorities = user.getRoles();
        return new MyUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFullName(),
                user.getPassword(),
                authorities);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities =  authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyUserDetails userDetails = (MyUserDetails) obj;
        return Objects.equals(id, userDetails.id);
    }
}
