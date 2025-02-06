package com.example.FurnitureLand.controller;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<Billing> createBilling(@RequestBody Billing billing) {
        Billing createdBilling = billingService.createBilling(billing);
        return ResponseEntity.status(201).body(createdBilling);
    }

    @DeleteMapping("/cancel/{billingId}")
    public ResponseEntity<String> cancelBilling(@PathVariable Long billingId) {
        billingService.cancelBilling(billingId);
        return ResponseEntity.ok("Billing canceled successfully");
    }
}
