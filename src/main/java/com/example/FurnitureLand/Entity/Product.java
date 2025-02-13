package com.example.FurnitureLand.Entity;

import com.example.FurnitureLand.Enum.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"productCode\"", nullable = false)
    private String productCode;
    @Column(name = "\"hsnNumber\"")
    private String hsnNumber;
    @Column(name = "\"costPrice\"")
    private double costPrice;
    @Column(name = "\"marketPrice\"")
    private double marketPrice;
    private String description;
    private String color;
    @Column(nullable = false)
    private Integer quantity;
    private String manufacturer;
    @Enumerated(EnumType.STRING)
    private Status status;

}
