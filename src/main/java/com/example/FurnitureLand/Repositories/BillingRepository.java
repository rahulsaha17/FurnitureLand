package com.example.FurnitureLand.Repositories;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByCustomer(Customer customer);
}
