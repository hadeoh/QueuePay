package com.decagon.queuepay.controller;

import com.decagon.queuepay.payload.Analytics;
import com.decagon.queuepay.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping(path = "business/{businessId}/analytics")
    public ResponseEntity<Analytics> analysis(@PathVariable(value = "businessId") Integer businessId){
        return new ResponseEntity<Analytics>(analyticsService.getAnalytics(businessId), HttpStatus.OK);
    }
}
