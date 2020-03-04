package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessRepository extends JpaRepository<Business, UUID> {
}
