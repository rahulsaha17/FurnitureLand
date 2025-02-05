package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Repositories.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public Billing createBilling(Billing billing) {
        return billingRepository.save(billing);
    }
}