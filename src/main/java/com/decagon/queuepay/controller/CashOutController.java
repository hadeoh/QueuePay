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

    @RequestMapping(path = "business/{businessId}/wallet/{walletId}/cashout", method = RequestMethod.PATCH)
    public ResponseEntity<Transaction> cashOut(
            @PathVariable(value = "businessId") UUID businessId,
            @PathVariable(value = "walletId") UUID walletId,
            @Valid @RequestBody CashOut cashOut) throws Exception {
        return new ResponseEntity<>(cashOutService.cashOut(businessId, walletId, cashOut), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "business/{businessId}/wallet/{walletId}/analytics", method = RequestMethod.GET)
    public ResponseEntity<Analytics> analysis(@PathVariable(value = "businessId") UUID businessId,
                                              @PathVariable(value = "walletId") UUID walletId){
        return new ResponseEntity<>(cashOutService.getAnalytics(businessId, walletId), HttpStatus.OK);
    }

}
