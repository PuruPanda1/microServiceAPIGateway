package com.encora.purab.product_service.controller;

import com.encora.purab.product_service.dto.ProductRequest;
import com.encora.purab.product_service.entity.Product;
import com.encora.purab.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/available/{productId}/{variantId}")
    public ResponseEntity<Boolean> isProductAvailable(@PathVariable Long productId, @PathVariable Long variantId, @RequestParam("requiredQuantity") Integer requiredQuantity) {
        return productService.isProductAvailable(productId, variantId, requiredQuantity);
    }

    @PostMapping("/decrease-quantity/{productId}/{variantId}")
    ResponseEntity<Void> decreaseProductStock(@PathVariable Long productId, @PathVariable Long variantId, @RequestParam("quantity") Integer quantity) {
        return productService.decreaseProductStock(productId, variantId, quantity);
    }

}
