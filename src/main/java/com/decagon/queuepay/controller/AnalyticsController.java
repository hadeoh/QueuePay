package com.decagon.queuepay.controller;

import com.decagon.queuepay.payload.Analytics;
import com.decagon.queuepay.response.Message;
import com.decagon.queuepay.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping(path = "business/{businessId}/analytics")
    public ResponseEntity<?> analysis(@PathVariable(value = "businessId") Integer businessId){
        Analytics analytics = analyticsService.getAnalytics(businessId);
        if (analytics != null){
            return new ResponseEntity<Analytics>(analyticsService.getAnalytics(businessId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(new Message("No analytics for this business"));
    }
}
