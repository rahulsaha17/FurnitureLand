package com.example.FurnitureLand.Repositories;

import com.example.FurnitureLand.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductCode(String productCode);
    List<Product> findByHsnNumber(String hsnNumber);
}
