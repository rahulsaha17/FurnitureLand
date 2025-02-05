package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByHsn(String hsn) {
        return productRepository.findByHsnNumber(hsn);
    }

    public Product getProductByCode(String productCode) {
        Optional<Product> product = productRepository.findByProductCode(productCode);
        return product.orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
