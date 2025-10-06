package com.encora.purab.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Long productId;
    private Long productVariantId;
    private Integer quantity;
    private double productPrice;
    private double discountAmount;

    @Transient
    private double finalPrice;

    public double getFinalPrice() {
        if ((productPrice - discountAmount) <= 0)
            throw new ArithmeticException("Discount amount can not be greater or equal to the product price");
        this.finalPrice = (productPrice - discountAmount);
        return finalPrice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    @JsonIgnore
    private Order order;
}
