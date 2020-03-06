package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.BankAccount;
import com.decagon.queuepay.models.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
}
