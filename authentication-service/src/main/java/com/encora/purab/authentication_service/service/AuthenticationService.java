package com.encora.purab.authentication_service.service;

import com.encora.purab.authentication_service.dto.AuthRequest;
import com.encora.purab.authentication_service.dto.CustomerRequest;
import com.encora.purab.authentication_service.entity.UserCredential;
import com.encora.purab.authentication_service.exception.InvalidUserCredentials;
import com.encora.purab.authentication_service.exception.ServerIssueException;
import com.encora.purab.authentication_service.exception.UserAlreadyExistsException;
import com.encora.purab.authentication_service.repository.UserCredentialRepository;
import com.encora.purab.authentication_service.util.feign.CustomerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerInterface customerInterface;

    public String register(UserCredential userCredential) {
//        TODO check whether customer with same email exists in the db
        ResponseEntity<Long> customerId = customerInterface.getCustomerIdByEmail(userCredential.getEmail());
        if(customerId.getStatusCode() == HttpStatus.OK){
            throw new UserAlreadyExistsException("User with provided already exits!");
        }

        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
//        TODO call the customerController using FEIGN-CLIENT to add a new customer with basic details
        ResponseEntity<Void> response = customerInterface.createCustomer(new CustomerRequest(userCredential.getUsername(), "", userCredential.getEmail()));

        if(response.getStatusCode() != HttpStatus.OK){
            throw new ServerIssueException("Not able to register the customer");
        }

        return "User is registered";
    }

    public String generateToken(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated())
            return jwtService.generateToken(authRequest.getUsername());
        throw new InvalidUserCredentials("User or password does not match");
    }

//    TODO the function will return the customerId of the customer with the given auth token
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
