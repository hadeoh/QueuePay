package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String Email);
    Boolean existsByEmail(String email);
    User findByEmailVerificationToken(String token);
}
