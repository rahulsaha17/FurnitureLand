package com.example.FurnitureLand.Controller;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<?> createBilling(@RequestBody Billing billing) {
        try {
            Billing createdBilling = billingService.createBilling(billing);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBilling);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getBillings")
    public ResponseEntity<List<Billing>> getBillings() {
        List<Billing> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

    @DeleteMapping("/cancel/{billingId}")
    public ResponseEntity<?> cancelBilling(@PathVariable Long billingId) {
        try {
            billingService.cancelBilling(billingId);
            return ResponseEntity.ok("Billing canceled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
