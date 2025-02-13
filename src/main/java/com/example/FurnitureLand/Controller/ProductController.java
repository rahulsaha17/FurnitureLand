package com.example.FurnitureLand.Controller;

import com.example.FurnitureLand.Entity.Billing;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Enum.Status;
import com.example.FurnitureLand.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(201).body(savedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            Product product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getProductsByDetails")
    public ResponseEntity<?> getProductsByDetails(
            @RequestParam(required = false) String hsnNumber,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Status status) {

        if (hsnNumber == null && productCode == null && color == null && manufacturer == null && status == null) {
            return ResponseEntity.badRequest().body("Any of these 'hsnNumber', 'productCode', 'color', 'manufacturer', 'status' must have");
        }
        List<Product> products = productService.getProductsByFilters(hsnNumber, productCode, color, manufacturer, status);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/code/{productCode}")
    public ResponseEntity<List<Product>> getProductByCode(@PathVariable String productCode) {
        List<Product> products = productService.getProductsByCode(productCode);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}

