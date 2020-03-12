package com.decagon.queuepay.service;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.repositories.BusinessRepository;
import com.decagon.queuepay.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BusinessRepository businessRepository;

    public List<Wallet> findWalletsByBusinessId(Integer businessId) throws Exception {
        Optional<Business> business = businessRepository.findById(businessId);
        if (business.isEmpty()) {
            throw new Exception("Business not found");
        }
        return walletRepository.findByBusinessId(business.get().getId());
    }
}

