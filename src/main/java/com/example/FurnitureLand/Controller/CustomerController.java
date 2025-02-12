package com.example.FurnitureLand.Controller;

import com.example.FurnitureLand.Entity.Customer;
import com.example.FurnitureLand.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.addCustomer(customer);
            return ResponseEntity.status(201).body(savedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addCustomerDiscount/{phoneNumber}/{discountPercentage}")
    public ResponseEntity<Customer> addCustomerDiscount(@PathVariable String phoneNumber, @PathVariable Double discountPercentage) {
        Customer savedCustomer = customerService.addCustomerDiscount(phoneNumber, discountPercentage);
        return ResponseEntity.status(201).body(savedCustomer);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<?> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            Customer customer = customerService.getCustomerByPhoneNumber(phoneNumber);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }
}
