package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Repositories.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private CustomerService customerService;

    public Billing createBilling(Billing billing) {
        Customer customer = billing.getCustomer();
        if (customer.getDiscountPercentage() != null) {
            double discount = (billing.getTotalAmount() * customer.getDiscountPercentage()) / 100;
            billing.setTotalAmount(billing.getTotalAmount() - discount);

            // Remove coupon after use
            customer.setDiscountPercentage(null);
            customerService.addCustomer(customer);
        }
        return billingRepository.save(billing);
    }

    public void cancelBilling(Long billingId) {
        Billing billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found"));
        billingRepository.delete(billing);
    }
}