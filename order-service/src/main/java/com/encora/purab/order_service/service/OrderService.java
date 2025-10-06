package com.encora.purab.order_service.service;

import com.encora.purab.order_service.dto.OrderItemRequest;
import com.encora.purab.order_service.dto.OrderRequest;
import com.encora.purab.order_service.entity.Order;
import com.encora.purab.order_service.entity.OrderItem;
import com.encora.purab.order_service.exception.InvalidOrderRequestException;
import com.encora.purab.order_service.exception.ResourceNotFoundException;
import com.encora.purab.order_service.repository.OrderRepository;
import com.encora.purab.order_service.util.feign.CustomerInterface;
import com.encora.purab.order_service.util.feign.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerInterface customerInterface;

    @Autowired
    ProductInterface productInterface;

    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity(orderRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Order> getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent())
            return new ResponseEntity(order.get(), HttpStatus.OK);
        throw new ResourceNotFoundException("Order does not exist");
    }

    public ResponseEntity<Long> createOrder(OrderRequest orderRequest) {

//        check if customer is valid or not
//        TODO FUTURE -- USE JWT FOR VERIFICATION
        Long customerId = orderRequest.getCustomerId();
        if (customerInterface.verifyCustomerById(customerId).getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new InvalidOrderRequestException("Not a valid customer");
        }
//        check product availability for each product if fails then don't make order
        List<OrderItemRequest> orderItemList = orderRequest.getOrderItemList();

        for (OrderItemRequest orderItemRequest : orderItemList) {
            Boolean isProductAvailable = productInterface.isProductAvailable(orderItemRequest.getProductId(), orderItemRequest.getVariantId(), orderItemRequest.getProductQuantity()).getBody();
            if (!isProductAvailable) {
                throw new InvalidOrderRequestException("Product out of Stock");
            }
        }

        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemRequest.getProductId());
            orderItem.setProductVariantId(orderItemRequest.getVariantId());
            orderItem.setQuantity(orderItemRequest.getProductQuantity());
            orderItem.setProductPrice(orderItemRequest.getProductPrice());
            orderItem.setDiscountAmount(orderItemRequest.getDiscountAmount());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
//        update the product stock
            productInterface.decreaseProductStock(orderItem.getProductId(), orderItem.getProductVariantId(), orderItem.getQuantity());
        }

        orderRepository.save(order);

        return new ResponseEntity(order.getOrderId(), HttpStatus.OK);
    }
}
