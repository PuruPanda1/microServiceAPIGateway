package com.encora.purab.customer_service.service;

import com.encora.purab.customer_service.dto.AddressRequest;
import com.encora.purab.customer_service.dto.CustomerRequest;
import com.encora.purab.customer_service.entity.Address;
import com.encora.purab.customer_service.entity.Customer;
import com.encora.purab.customer_service.repository.CustomerRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity(customerRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Customer> getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent())
        return new ResponseEntity(customer.get(), HttpStatus.OK);
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Customer> createCustomer(CustomerRequest customerRequest) {

        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());

        if(customerRequest.getAddresses() != null){
            for (AddressRequest addressRequest : customerRequest.getAddresses()) {
                Address address = new Address();
                address.setAddressLine1(addressRequest.getAddressLine1());
                address.setAddressLine2(addressRequest.getAddressLine2());
                address.setCity(addressRequest.getCity());
                address.setPostalCode(addressRequest.getPostalCode());
                address.setCountry(addressRequest.getCountry());
                address.setPrimaryAddress(addressRequest.isPrimaryAddress());

                address.setCustomer(customer);
                customer.getAddressList().add(address);
            }
        }

        Customer savedCustomer = customerRepository.save(customer);
        if(savedCustomer == null){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(savedCustomer, HttpStatus.OK);
    }


}
