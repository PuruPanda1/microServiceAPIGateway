package com.encora.purab.order_service.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CUSTOMER-SERVICE")
public interface CustomerInterface {
    @GetMapping("customers/verify/{id}")
    ResponseEntity<Void> verifyCustomerById(@PathVariable Long id);
}
