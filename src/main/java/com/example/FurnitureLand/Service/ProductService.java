package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Enum.Status;
import com.example.FurnitureLand.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        if(product.getQuantity()==null || product.getQuantity()<0){
            throw new RuntimeException("Invalid product quantity.");
        } else if (product.getQuantity()>0 && Status.SOLD==product.getStatus()) {
            throw new RuntimeException("Product status cannot be sold for positive quantity product");
        }else {
            return productRepository.save(product);
        }
    }

    public List<Product> getProductsByHsn(String hsn) {
        return productRepository.findByHsnNumber(hsn);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOpt = productRepository.findById(id);

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Update fields
            existingProduct.setProductCode(updatedProduct.getProductCode());
            existingProduct.setHsnNumber(updatedProduct.getHsnNumber());
            existingProduct.setCostPrice(updatedProduct.getCostPrice());
            existingProduct.setMarketPrice(updatedProduct.getMarketPrice());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setColor(updatedProduct.getColor());
            if(updatedProduct.getQuantity()!=null && updatedProduct.getQuantity()<0){
                throw new RuntimeException("Invalid product quantity.");
            }
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setManufacturer(updatedProduct.getManufacturer());
            if (updatedProduct.getStatus() == Status.SELF_USED) {
                existingProduct.setStatus(updatedProduct.getStatus());
            } else if (updatedProduct.getStatus() == Status.SOLD && updatedProduct.getQuantity() > 0) {
                throw new RuntimeException("Product status cannot be sold for a product with positive quantity");
            } else if (updatedProduct.getStatus() != null) {
                existingProduct.setStatus(updatedProduct.getStatus());
            }

            return productRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public Product getProductByDescription(String productDescription) {
        return productRepository.findByDescription(productDescription);
    }
}
