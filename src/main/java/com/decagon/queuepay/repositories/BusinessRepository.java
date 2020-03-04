package com.decagon.queuepay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessRepository extends JpaRepository<BusinessRepository, UUID> {
}
