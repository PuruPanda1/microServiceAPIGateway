package com.encora.purab.authentication_service.service;

import com.encora.purab.authentication_service.dto.AuthRequest;
import com.encora.purab.authentication_service.entity.UserCredential;
import com.encora.purab.authentication_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String register(UserCredential userCredential){
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        return "User is registered";
    }

    public String generateToken(AuthRequest authRequest){
        return jwtService.generateToken(authRequest.getUsername());
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
