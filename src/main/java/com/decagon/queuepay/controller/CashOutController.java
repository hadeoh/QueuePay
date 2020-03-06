package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.payload.CashOut;
import com.decagon.queuepay.service.CashOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CashOutController {

    @Autowired
    private CashOutService cashOutService;

    @PatchMapping(path = "business/{businessId}/wallet/{walletId}/cashout")
    public ResponseEntity<Transaction> cashOut(
            @PathVariable(value = "businessId") Integer businessId,
            @PathVariable(value = "walletId") Integer walletId,
            @Valid @RequestBody CashOut cashOut) throws Exception {
        return new ResponseEntity<Transaction>(cashOutService.cashOut(businessId, walletId, cashOut), HttpStatus.OK);
    }

}
