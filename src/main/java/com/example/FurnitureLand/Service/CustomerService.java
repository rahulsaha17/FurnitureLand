package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer addCustomerDiscount(String phoneNumber, Double discountPercentage) {
        Customer customer = getCustomerByPhoneNumber(phoneNumber);
        customer.setDiscountPercentage(discountPercentage);
        return customerRepository.save(customer);
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElse(null);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(id);

        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            // Update fields
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setDiscountPercentage(updatedCustomer.getDiscountPercentage());

            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
    }
}
