package com.encora.purab.authentication_service.util.feign;

import com.encora.purab.authentication_service.dto.CustomerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("CUSTOMER-SERVICE")
public interface CustomerInterface {
    @GetMapping("/customers/customer_id")
    public ResponseEntity<Long> getCustomerIdByEmail(@RequestParam String email);

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerRequest customerRequest);
}
