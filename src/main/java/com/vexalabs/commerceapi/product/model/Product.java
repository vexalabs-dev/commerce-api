package com.vexalabs.commerceapi.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "sale_price", nullable = false, scale = 2)
    private BigDecimal salePrice;

    public Product(String name, String description, BigDecimal salePrice) {
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
    }
}
