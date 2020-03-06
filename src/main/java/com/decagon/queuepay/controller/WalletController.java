package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet")
    public ResponseEntity getWallet(@RequestBody Business business){
         Wallet wallet = walletService.walletByBusinessId(business);
         return  ResponseEntity.ok().body(wallet);
    }

    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getAllWallets(){
         List wallets = walletService.findAllWallets();
         return  ResponseEntity.ok().body(wallets);
    }
}
