package com.encora.purab.customer_service.dto.order;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private Long variantId;
    private Integer productQuantity;
    private double productPrice;
    private double discountAmount;
}