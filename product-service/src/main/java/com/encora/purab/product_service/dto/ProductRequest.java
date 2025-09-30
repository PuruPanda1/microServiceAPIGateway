package com.encora.purab.product_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String productName;
    private String productDescription;

    private List<ProductVariantRequest> productVariantRequestList;
}
