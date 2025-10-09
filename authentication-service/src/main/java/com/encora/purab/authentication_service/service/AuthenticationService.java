package com.encora.purab.authentication_service.service;

import com.encora.purab.authentication_service.dto.AuthRequest;
import com.encora.purab.authentication_service.dto.CustomerRequest;
import com.encora.purab.authentication_service.entity.UserCredential;
import com.encora.purab.authentication_service.exception.InvalidUserCredentials;
import com.encora.purab.authentication_service.exception.ServerIssueException;
import com.encora.purab.authentication_service.exception.UserAlreadyExistsException;
import com.encora.purab.authentication_service.repository.UserCredentialRepository;
import com.encora.purab.authentication_service.util.feign.CustomerInterface;
import feign.FeignException;
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

        try {
            ResponseEntity<Long> isCustomerAvailable = customerInterface.getCustomerIdByEmail(userCredential.getEmail());
            if (isCustomerAvailable.getStatusCode() == HttpStatus.OK) {
                throw new UserAlreadyExistsException("User with provided email already exists!");
            }
        } catch (FeignException.NotFound ex) {
            createCustomer(userCredential);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "User is registered";

    }

    private void createCustomer(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        UserCredential savedUserCredential = userCredentialRepository.save(userCredential);

//      TODO TEMP FIX sets the customer first name as email which can be later changed in profile
        ResponseEntity<Void> isCustomerCreated = customerInterface.createCustomer(new CustomerRequest(userCredential.getEmail(), "", userCredential.getEmail()));

        if(isCustomerCreated.getStatusCode() != HttpStatus.OK){
            userCredentialRepository.delete(savedUserCredential);
            throw new ServerIssueException("Not able to register the customer");
        }
    }

    public String generateToken(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated())
            return jwtService.generateToken(authRequest.getEmail());
        throw new InvalidUserCredentials("User or password does not match");
    }

    public ResponseEntity<Long> validateToken(String token) {
        String email = jwtService.validateTokenAndReturnEmail(token);
        ResponseEntity<Long> customerId = customerInterface.getCustomerIdByEmail(email);
        return customerId;
    }

}
