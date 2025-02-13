package com.example.FurnitureLand.Repositories;

import com.example.FurnitureLand.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByProductCode(String productCode);
    List<Product> findByHsnNumber(String hsnNumber);
}
