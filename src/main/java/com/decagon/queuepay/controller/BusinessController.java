package com.decagon.queuepay.controller;

import com.decagon.queuepay.models.Business;
import com.decagon.queuepay.payload.BusinessDto;
import com.decagon.queuepay.response.Message;
import com.decagon.queuepay.service.BusinessService;
import com.decagon.queuepay.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/business")
@CrossOrigin(value = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public ResponseEntity<List<Business>> getAllBusiness(){
        List<Business> allBusiness = businessService.findAllBusiness();
       return ResponseEntity.ok().body(allBusiness);
    }

    @PostMapping
    public ResponseEntity<?> businessReg(@RequestBody @Valid BusinessDto businessDto, BindingResult bindingResult, HttpServletRequest request)
            throws Exception {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null){
            return errorMap;
        }
        businessService.businessRegistration(businessDto, request);
        return ResponseEntity.ok(new Message("Registration successful!"));
    }
}
