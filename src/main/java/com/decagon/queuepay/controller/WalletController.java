package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
@CrossOrigin
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("{businessId}/wallets")
    public ResponseEntity<List<Wallet>> getAllWallets(@PathVariable Integer businessId) throws Exception {
         List<Wallet> wallets = walletService.findWalletsByBusinessId(businessId);
         return ResponseEntity.ok().body(wallets);
    }
}
