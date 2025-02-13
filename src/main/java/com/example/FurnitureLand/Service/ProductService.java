package com.example.FurnitureLand.Service;

import com.example.FurnitureLand.Entity.BillingProduct;
import com.example.FurnitureLand.Entity.Product;
import com.example.FurnitureLand.Enum.Status;
import com.example.FurnitureLand.Repositories.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public Optional<Product> getProductByBillingProductDetails(BillingProduct billingProduct) {
        if(billingProduct.getProductCode()!=null){
            List<Product> productsByCode = getProductsByCode(billingProduct.getProductCode());
            if(!productsByCode.isEmpty()){
                if(billingProduct.getColor()!=null){
                    return productsByCode.stream().filter(product -> product.getColor().equalsIgnoreCase(billingProduct.getColor())).findFirst();
                }else {
                    return Optional.ofNullable(productsByCode.get(0));
                }
            }

        }
        return Optional.empty();
    }

    public List<Product> getProductsByFilters(String hsnNumber, String productCode, String color, String manufacturer, Status status) {
        return productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (hsnNumber != null && !hsnNumber.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("hsnNumber"), hsnNumber));
            }
            if (productCode != null && !productCode.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("productCode"), productCode));
            }
            if (color != null && !color.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("color"), color));
            }
            if (manufacturer != null && !manufacturer.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("manufacturer"), manufacturer));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
