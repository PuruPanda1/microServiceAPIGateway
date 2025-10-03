package com.encora.purab.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @GetMapping("fallback/orders")
    public Mono<String> orderServiceFailed(){
        return Mono.just("Service unavailable");
    }

}
