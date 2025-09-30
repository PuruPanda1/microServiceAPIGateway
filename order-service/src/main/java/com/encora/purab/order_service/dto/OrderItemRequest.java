package com.encora.purab.order_service.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private double productPrice;
    private double discountAmount;
}
