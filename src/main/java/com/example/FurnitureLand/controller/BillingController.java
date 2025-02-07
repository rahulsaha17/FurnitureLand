package com.example.FurnitureLand.controller;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<Billing> createBilling(@RequestBody Billing billing) {
        Billing createdBilling = billingService.createBilling(billing);
        if(createdBilling==null){
            ResponseEntity.status(400).body("Product is not present.");
        }
        return ResponseEntity.status(201).body(createdBilling);
    }

    @GetMapping("/getBillings")
    public ResponseEntity<List<Billing>> getBillings() {
        List<Billing> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

    @DeleteMapping("/cancel/{billingId}")
    public ResponseEntity<String> cancelBilling(@PathVariable Long billingId) {
        billingService.cancelBilling(billingId);
        return ResponseEntity.ok("Billing canceled successfully");
    }
}
