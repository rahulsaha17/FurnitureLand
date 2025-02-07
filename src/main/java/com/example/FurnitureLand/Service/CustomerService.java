package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
