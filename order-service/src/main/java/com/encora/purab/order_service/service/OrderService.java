package com.encora.purab.order_service.service;

import com.encora.purab.order_service.dto.OrderItemRequest;
import com.encora.purab.order_service.dto.OrderRequest;
import com.encora.purab.order_service.entity.Order;
import com.encora.purab.order_service.entity.OrderItem;
import com.encora.purab.order_service.exception.ResourceNotFoundException;
import com.encora.purab.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity(orderRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Order> getOrderById(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent())
            return new ResponseEntity(order.get(), HttpStatus.OK);
        throw new ResourceNotFoundException("Order does not exist");
    }

    public ResponseEntity<Order> createOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());

        for(OrderItemRequest orderItemRequest : orderRequest.getOrderItemList()){
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemRequest.getProductId());
            orderItem.setProductPrice(orderItemRequest.getProductPrice());
            orderItem.setDiscountAmount(orderItemRequest.getDiscountAmount());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);
        order.getOrderTotal();
        return new ResponseEntity(order, HttpStatus.OK);
    }
}
