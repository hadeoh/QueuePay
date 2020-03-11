package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.transaction.Transaction;
import com.decagon.queuepay.payload.CashOut;
import com.decagon.queuepay.service.CashOutService;
import com.decagon.queuepay.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CashOutController {

    @Autowired
    private CashOutService cashOutService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PatchMapping(path = "business/{businessId}/wallet/{walletId}/cashout")
    public ResponseEntity<?> cashOut(
            @PathVariable(value = "businessId") Integer businessId,
            @PathVariable(value = "walletId") Integer walletId,
            @Valid @RequestBody CashOut cashOut, BindingResult bindingResult) throws Exception {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null){
            return errorMap;
        }
        return new ResponseEntity<>(cashOutService.cashOut(businessId, walletId, cashOut), HttpStatus.OK);
    }

}
