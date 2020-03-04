package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmailIgnoreCase(String email);
}
