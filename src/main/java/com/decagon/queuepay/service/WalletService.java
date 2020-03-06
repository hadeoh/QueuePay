package com.decagon.queuepay.service;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.repositories.BusinessRepository;
import com.decagon.queuepay.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BusinessRepository businessRepository;

    public Wallet walletByBusinessId(Business business){
//        Business business1 = businessRepository.getOne(business.getId());
        return walletRepository.findByBusinessId(business.getId());
    }

    public List<Wallet> findAllWallets(){
        return walletRepository.findAll();
    }
}

