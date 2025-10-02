package com.encora.purab.order_service.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PRODUCT-SERVICE")
public interface ProductInterface {
    @GetMapping("products/available/{productId}/{variantId}")
    public ResponseEntity<Boolean> isProductAvailable(@PathVariable Long productId, @PathVariable Long variantId, @RequestParam("requiredQuantity") Integer requiredQuantity);

    @PostMapping("products/decrease-quantity/{productId}/{variantId}")
    ResponseEntity<Void> decreaseProductStock(@PathVariable Long productId, @PathVariable Long variantId, @RequestParam("quantity") Integer quantity);
}
