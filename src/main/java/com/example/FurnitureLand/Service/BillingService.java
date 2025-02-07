package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Repositories.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public Billing createBilling(Billing billing) {
        if(CollectionUtils.isEmpty(billing.getProducts()) || billing.getProducts().stream().anyMatch(product -> productService.hasProduct(product.getId()))){
            return null;
        }
        List<Product> products = productService.getProducts(billing.getProducts());
        billing.setProducts(products);
        if(billing.getCustomer()!=null && billing.getCustomer().getId()!=null){
            Long customerId = billing.getCustomer().getId();
            Customer customer = customerService.getCustomerById(customerId);
            if (customer!=null && customer.getDiscountPercentage() != null) {
                double discount = (billing.getTotalAmount() * customer.getDiscountPercentage()) / 100;
                billing.setTotalAmount(billing.getTotalAmount() - discount);

                // Remove discount after use
                customer.setDiscountPercentage(null);
                billing.setCustomer(customer);
                customerService.addCustomer(customer);
            }
        }
        return billingRepository.save(billing);
    }

    public void cancelBilling(Long billingId) {
        Billing billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found"));
        billingRepository.delete(billing);
    }

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }
}