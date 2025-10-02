package com.encora.purab.order_service.controller;

import com.encora.purab.order_service.dto.OrderRequest;
import com.encora.purab.order_service.entity.Order;
import com.encora.purab.order_service.exception.EmptyOrderException;
import com.encora.purab.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }

    @PostMapping("")
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequest orderRequest){
        if(orderRequest.getOrderItemList().isEmpty()){
            throw new EmptyOrderException("Order without order items is not allowed");
        }
        return orderService.createOrder(orderRequest);
    }
}
