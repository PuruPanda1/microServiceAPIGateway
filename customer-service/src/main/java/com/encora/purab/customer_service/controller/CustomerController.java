package com.encora.purab.customer_service.controller;

import com.encora.purab.customer_service.dto.customer.CustomerRequest;
import com.encora.purab.customer_service.dto.order.OrderRequest;
import com.encora.purab.customer_service.dto.order.OrderResponse;
import com.encora.purab.customer_service.entity.Customer;
import com.encora.purab.customer_service.service.CustomerService;
import com.encora.purab.customer_service.util.feign.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderInterface orderInterface;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("verify/{id}")
    public ResponseEntity<Void> verifyCustomerById(@PathVariable Long id) {
        return customerService.verifyCustomerById(id);
    }

    @GetMapping("/customer_id")
    public ResponseEntity<Long> getCustomerIdByEmail(@RequestParam String email) {
        return customerService.getCustomerIdByEmail(email);
    }

    @PostMapping("")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    //    order creation for the user! -- returns the orderId
    @PostMapping("/create/order")
    public ResponseEntity<OrderResponse> createOrderForCustomer(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderInterface.createOrder(orderRequest).getBody());
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
