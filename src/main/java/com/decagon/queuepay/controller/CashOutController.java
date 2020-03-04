package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.CashOut;
import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.service.CashOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class CashOutController {

    @Autowired
    private CashOutService cashOutService;

    @RequestMapping(path = "business/{businessId}/wallet/{walletId}/cashout", method = RequestMethod.POST)
    public Transaction businessCashOut(
            @PathVariable(value = "businessId") UUID businessId,
            @PathVariable(value = "walletId") UUID walletId,
            @Valid @RequestBody CashOut cashOut){
        return cashOutService.businessCashOut(businessId, walletId, cashOut);
    }
}
