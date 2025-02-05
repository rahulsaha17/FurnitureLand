package com.example.FurnitureLand.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productCode;  // Unique product code

    private String hsnNumber;
    private double costPrice;
    private double marketPrice;
    private String description;
    private String color;
    private String manufacturer;
    private String status;
    @ManyToOne
    @JoinColumn(name = "billing_id")  // Foreign Key in Product Table
    private Billing billing;

}
