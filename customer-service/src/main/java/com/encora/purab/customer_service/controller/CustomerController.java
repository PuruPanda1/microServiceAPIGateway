package com.encora.purab.customer_service.controller;

import com.encora.purab.customer_service.dto.AddressRequest;
import com.encora.purab.customer_service.dto.CustomerRequest;
import com.encora.purab.customer_service.entity.Address;
import com.encora.purab.customer_service.entity.Customer;
import com.encora.purab.customer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.createCustomer(customerRequest);
    }
}
