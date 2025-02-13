package com.example.FurnitureLand.Repositories;

import com.example.FurnitureLand.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductCode(String productCode);
    List<Product> findByHsnNumber(String hsnNumber);
    List<Product> findByDescription(String description);
}
