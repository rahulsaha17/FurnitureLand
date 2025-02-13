package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.BillingProduct;
import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Enum.Status;
import com.example.FurnitureLand.Repositories.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        if (CollectionUtils.isEmpty(billing.getPurchasedProducts())) {
            throw new RuntimeException("Billing must have at least one product.");
        }

        double totalAmount = 0.0;
        List<BillingProduct> billingProductList = new ArrayList<>();

        // Validate product availability & store product snapshot
        for (BillingProduct billingProduct : billing.getPurchasedProducts()) {
            if (billingProduct.getProductCode()==null) {
                throw new RuntimeException("Billing product must have a product code.");
            }
            Optional<Product> existingProduct = productService.getProductByBillingProductDetails(billingProduct);

            if (existingProduct.isEmpty()) {
                throw new RuntimeException("Product with code " + billingProduct.getProductCode() + "and color" + billingProduct.getColor() + " not found.");
            }

            if (billingProduct.getQuantity() > existingProduct.get().getQuantity()) {
                throw new RuntimeException("Insufficient stock for product ID " + existingProduct.get().getId());
            }

            // Reduce stock & update status
            existingProduct.get().setQuantity(existingProduct.get().getQuantity() - billingProduct.getQuantity());
            if (existingProduct.get().getQuantity() == 0) {
                existingProduct.get().setStatus(Status.SOLD);
            }
            productService.updateProduct(existingProduct.get().getId(), existingProduct.get());

            // Create snapshot of the purchased product
            BillingProduct newBillingProduct = new BillingProduct();
            newBillingProduct.setBilling(billing);
            newBillingProduct.setPriceAtPurchase(existingProduct.get().getMarketPrice());
            newBillingProduct.setQuantity(billingProduct.getQuantity());

            billingProductList.add(newBillingProduct);
            totalAmount += newBillingProduct.getPriceAtPurchase() * newBillingProduct.getQuantity();
        }

        // Apply customer discount if available
        if (customer.getDiscountPercentage() != null) {
            double discount = (totalAmount * customer.getDiscountPercentage()) / 100;
            totalAmount -= discount;

            // Remove discount after use
            customer.setDiscountPercentage(null);
            customerService.updateCustomer(customer.getId(), customer);
        }

        billing.setTotalAmount(totalAmount);
        billing.setCustomer(customer);
        billing.setPurchasedProducts(billingProductList);

        return billingRepository.save(billing);
    }


    public void cancelBilling(Long billingId) {
        Billing billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found"));

        // Restore product quantities
        for (BillingProduct billingProduct : billing.getPurchasedProducts()) {
            Optional<Product> existingProduct = productService.getProductByBillingProductDetails(billingProduct);

            if (existingProduct.isPresent()) {
                existingProduct.get().setQuantity(existingProduct.get().getQuantity() + billingProduct.getQuantity());

                // Change status back to AVAILABLE if it was SOLD
                if (existingProduct.get().getStatus() == Status.SOLD) {
                    existingProduct.get().setStatus(Status.AVAILABLE);
                }

                productService.updateProduct(existingProduct.get().getId(), existingProduct.get());
            }
        }

        billingRepository.delete(billing);
    }


    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }
}