package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    BankAccount findByBusinessId(UUID businessId);
}
