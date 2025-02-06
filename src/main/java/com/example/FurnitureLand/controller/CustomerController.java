package com.example.FurnitureLand.controller;

import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(201).body(savedCustomer);
    }

    @PostMapping("/addCustomerDiscount/{phoneNumber}/{discountPercentage}")
    public ResponseEntity<Customer> addCustomerDiscount(@PathVariable String phoneNumber, @PathVariable Double discountPercentage) {
        Customer savedCustomer = customerService.addCustomerDiscount(phoneNumber, discountPercentage);
        return ResponseEntity.status(201).body(savedCustomer);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Customer> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Customer customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(customer);
    }
}
