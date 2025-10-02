package com.encora.purab.customer_service.dto.customer;

import lombok.Data;

@Data
public class AddressRequest {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;
    private boolean primaryAddress = false;
}
