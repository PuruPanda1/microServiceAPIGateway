package com.encora.purab.apigateway.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("AUTHENTICATION-SERVICE")
public interface AuthenticationInterface {
    @GetMapping("/validate")
    public void validate(@RequestParam String token);
}
