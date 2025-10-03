package com.encora.purab.authentication_service.controller;

import com.encora.purab.authentication_service.dto.AuthRequest;
import com.encora.purab.authentication_service.entity.UserCredential;
import com.encora.purab.authentication_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(@RequestBody UserCredential userCredential){
        return authenticationService.register(userCredential);
    }

    @GetMapping("/generate")
    public String generate(@RequestBody AuthRequest authRequest){
        return authenticationService.generateToken(authRequest);
    }

    @GetMapping("/validate")
    public void validate(@RequestParam String token){
        authenticationService.validateToken(token);
    }
}
