package com.example.FurnitureLand.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "billing_products")
public class BillingProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "billing_id")
    @JsonBackReference
    private Billing billing;
    @Column(name = "\"priceAtPurchase\"", nullable = false)
    private double priceAtPurchase;
    @Column(name = "\"productCode\"", nullable = false)
    private String productCode;
    private String color;
    @Column(nullable = false)
    private int quantity;

    public BillingProduct() {}

    public BillingProduct(Product product, Billing billing, int quantity) {
        this.billing = billing;
        this.priceAtPurchase = product.getMarketPrice();
        this.quantity = quantity;
    }
}

