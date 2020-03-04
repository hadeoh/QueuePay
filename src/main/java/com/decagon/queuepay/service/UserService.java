package com.decagon.queuepay.service;

import com.decagon.queuepay.dto.UserRegistrationDto;
import com.decagon.queuepay.models.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmailIgnoreCase(String email);

    User save(UserRegistrationDto registration);

}
