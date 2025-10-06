package com.encora.purab.customer_service.util.feign;

import com.encora.purab.customer_service.dto.order.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ORDER-SERVICE")
public interface OrderInterface {

    @PostMapping("orders")
    ResponseEntity<Long> createOrder(@RequestBody OrderRequest orderRequest);

}
