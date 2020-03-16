package com.decagon.queuepay.service;

import com.decagon.queuepay.models.user.User;
import com.decagon.queuepay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " was not found"));
    }

    @Transactional
    public User loadByEmail(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
