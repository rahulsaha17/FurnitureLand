package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Enum.Status;
import com.example.FurnitureLand.Repositories.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public Billing createBilling(Billing billing) {
        if (billing.getCustomer() == null) {
            throw new RuntimeException("Customer is mandatory for billing.");
        }

        // Check if customer already exists, if not, add them
        Customer customer = billing.getCustomer();
        if (customer.getId() == null || customerService.getCustomerById(customer.getId()) == null) {
            customer = customerService.addCustomer(customer);
        }

        if (CollectionUtils.isEmpty(billing.getProducts())) {
            throw new RuntimeException("Billing must have at least one product.");
        }
        // Validate product availability
        for (Product orderedProduct : billing.getProducts()) {
            Product existingProduct = productService.getProductById(orderedProduct.getId());

            if (existingProduct == null) {
                throw new RuntimeException("Product with ID " + orderedProduct.getId() + " not found.");
            }

            if (orderedProduct.getQuantity() > existingProduct.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product ID " + orderedProduct.getId());
            }
        }

        // If validation passes, update product quantities
        List<Product> updatedProducts = billing.getProducts().stream().map(orderedProduct -> {
            Product existingProduct = productService.getProductById(orderedProduct.getId());

            // Reduce quantity
            int updatedQuantity = existingProduct.getQuantity() - orderedProduct.getQuantity();
            existingProduct.setQuantity(updatedQuantity);

            // If quantity becomes zero, mark status as SOLD
            if (updatedQuantity == 0) {
                existingProduct.setStatus(Status.SOLD);
            }

            return productService.updateProduct(existingProduct.getId(), existingProduct);
        }).collect(Collectors.toList());

        billing.setProducts(updatedProducts);

        // Apply customer discount if available
        if (customer.getDiscountPercentage() != null) {
            double discount = (billing.getTotalAmount() * customer.getDiscountPercentage()) / 100;
            billing.setTotalAmount(billing.getTotalAmount() - discount);

            // Remove discount after use
            customer.setDiscountPercentage(null);
            customerService.updateCustomer(customer.getId(), customer);
        }
        billing.setCustomer(customer);
        return billingRepository.save(billing);
    }

    public void cancelBilling(Long billingId) {
        Billing billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found"));

        // Restore product quantities
        billing.getProducts().forEach(product -> {
            Product existingProduct = productService.getProductById(product.getId());

            existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());

            // Change status back to IN_STOCK if it was SOLD
            if (existingProduct.getStatus() == Status.SOLD) {
                existingProduct.setStatus(Status.IN_STOCK);
            }

            productService.updateProduct(existingProduct.getId(), existingProduct);
        });

        billingRepository.delete(billing);
    }

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }
}