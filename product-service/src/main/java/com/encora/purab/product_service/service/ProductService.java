package com.encora.purab.product_service.service;

import com.encora.purab.product_service.dto.ProductRequest;
import com.encora.purab.product_service.dto.ProductVariantRequest;
import com.encora.purab.product_service.entity.Product;
import com.encora.purab.product_service.entity.ProductVariant;
import com.encora.purab.product_service.exception.ResourceNotFoundException;
import com.encora.purab.product_service.repository.ProductRepository;
import com.encora.purab.product_service.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Product> getProductById(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent())
            return new ResponseEntity(product, HttpStatus.OK);
        throw new ResourceNotFoundException("Product does not exist");
    }

    public ResponseEntity<Product> createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());

        for(ProductVariantRequest productVariantRequest : productRequest.getProductVariantRequestList()){
            ProductVariant productVariant = new ProductVariant();
            productVariant.setSize(productVariantRequest.getSize());
            productVariant.setColor(productVariantRequest.getColor());
            productVariant.setImage(productVariantRequest.getImage());
            productVariant.setUsualPrice(productVariantRequest.getUsualPrice());
            productVariant.setSalePrice(productVariantRequest.getSalePrice());
            productVariant.setStock(productVariantRequest.getStock());
            productVariant.setProduct(product);
            product.getProductVariantList().add(productVariant);
        }

        productRepository.save(product);
        return new ResponseEntity(product, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> isProductAvailable(Long productId, Long variantId, Integer requiredQuantity){
        Optional<ProductVariant> productVariantOptional = productVariantRepository.getProductVariant(productId, variantId);
        if(productVariantOptional.isPresent())
            if(productVariantOptional.get().getStock() >= requiredQuantity){
                return new ResponseEntity(true, HttpStatus.OK);
            }else{
                return new ResponseEntity(false, HttpStatus.OK);
            }
        throw new ResourceNotFoundException("Product or variant does not exist");
    }

    public ResponseEntity<Void> decreaseProductStock(Long productId, Long variantId, Integer quantity) {
//        get product variant by productId and variantId
        Optional<ProductVariant> productVariantOptional = productVariantRepository.getProductVariant(productId, variantId);
        if(!productVariantOptional.isPresent()){
            throw new ResourceNotFoundException("Product or variant does not exist");
        }

        ProductVariant productVariant = productVariantOptional.get();

        Integer currentStock = productVariant.getStock();
        productVariant.setStock(currentStock - quantity);
        productVariantRepository.save(productVariant);
        return new ResponseEntity(HttpStatus.OK);
    }
}
