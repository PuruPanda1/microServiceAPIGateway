package com.encora.purab.customer_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String email;

    private List<AddressRequest> addresses;
}
