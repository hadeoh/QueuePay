package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.models.wallet.Wallet;
import com.decagon.queuepay.payload.BusinessDto;
import com.decagon.queuepay.payload.BusinessRegDto;
import com.decagon.queuepay.payload.WalletDto;
import com.decagon.queuepay.response.Message;
import com.decagon.queuepay.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Business>> getAllBusiness(){
        List<Business> allBusiness = businessService.findAllBusiness();
       return ResponseEntity.ok().body(allBusiness);
    }
    @PostMapping
    public ResponseEntity<?> businessRegistration(@RequestBody BusinessRegDto businessRegDto, HttpServletRequest request)
            throws Exception {
        businessService.businessRegistration(modelMapper.map(businessRegDto.getBusinessDto(), Business.class),
                modelMapper.map(businessRegDto.getWalletDto(), Wallet.class), request);
        return ResponseEntity.ok(new Message("Registration successful!"));
    }
}
