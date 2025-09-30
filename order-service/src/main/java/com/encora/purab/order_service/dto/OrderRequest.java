package com.encora.purab.order_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;

    private List<OrderItemRequest> orderItemList;
}
