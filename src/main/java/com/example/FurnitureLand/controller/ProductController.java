package com.example.FurnitureLand.controller;

import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.status(201).body(savedProduct);
    }

    @GetMapping("/hsn/{hsn}")
    public ResponseEntity<List<Product>> getProductsByHsn(@PathVariable String hsn) {
        List<Product> products = productService.getProductsByHsn(hsn);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/code/{productCode}")
    public ResponseEntity<Product> getProductByCode(@PathVariable String productCode) {
        Product product = productService.getProductByCode(productCode);
        return ResponseEntity.ok(product);
    }
}

