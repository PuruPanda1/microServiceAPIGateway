package com.encora.purab.product_service.repository;

import com.encora.purab.product_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productVariantId=:variantId AND pv.product.productId=:productId")
    Optional<ProductVariant> getProductVariant(@Param("productId") Long productId, @Param("variantId") Long variantId);
}
