package com.encora.purab.product_service.dto;

import lombok.Data;

@Data
public class ProductVariantRequest {
    private String size;
    private String color;
    private String image;
    private double usualPrice;
    private double salePrice;

    private int stock;
}
