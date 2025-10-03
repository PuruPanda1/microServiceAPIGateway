package com.encora.purab.authentication_service.service;

import com.encora.purab.authentication_service.config.CustomUserDetails;
import com.encora.purab.authentication_service.entity.UserCredential;
import com.encora.purab.authentication_service.exception.InvalidUserCredentials;
import com.encora.purab.authentication_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUserName(username);
        return userCredential.map(CustomUserDetails::new).orElseThrow(()->new InvalidUserCredentials("User not found with username = " + username));
    }
}
