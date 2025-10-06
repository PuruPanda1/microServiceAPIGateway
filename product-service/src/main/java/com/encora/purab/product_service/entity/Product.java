package com.encora.purab.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String productDescription;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariantList = new ArrayList<>();

    @Transient
    private int stock;

    public int getStock() {
        int stock = 0;
        for (ProductVariant productVariant : productVariantList) {
            stock += productVariant.getStock();
        }
        this.stock = stock;
        return stock;
    }

}
