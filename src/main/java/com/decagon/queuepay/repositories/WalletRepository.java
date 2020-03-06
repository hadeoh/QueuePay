package com.decagon.queuepay.repositories;

import com.decagon.queuepay.models.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByBusinessId(Integer businessId);
}
