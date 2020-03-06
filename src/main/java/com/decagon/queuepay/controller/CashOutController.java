package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.payload.Analytics;
import com.decagon.queuepay.payload.CashOut;
import com.decagon.queuepay.service.CashOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class CashOutController {

    private CashOutService cashOutService;
    @Autowired
    public void cashOutService(CashOutService cashOutService) {
        this.cashOutService = cashOutService;
    }

    @PatchMapping(path = "business/{businessId}/wallet/{walletId}/cashout")
    public ResponseEntity<Transaction> cashOut(
            @PathVariable(value = "businessId") Integer businessId,
            @PathVariable(value = "walletId") Integer walletId,
            @Valid @RequestBody CashOut cashOut) throws Exception {
        return new ResponseEntity<Transaction>(cashOutService.cashOut(businessId, walletId, cashOut), HttpStatus.OK);
    }

    @GetMapping(path = "business/{businessId}/analytics")
    public ResponseEntity<Analytics> analysis(@PathVariable(value = "businessId") Integer businessId){
        return new ResponseEntity<Analytics>(cashOutService.getAnalytics(businessId), HttpStatus.OK);
    }

}
