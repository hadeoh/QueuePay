package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
