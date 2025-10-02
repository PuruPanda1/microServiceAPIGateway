package com.encora.purab.customer_service.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;

    private List<OrderItemRequest> orderItemList;
}