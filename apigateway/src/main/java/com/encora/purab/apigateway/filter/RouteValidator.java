package com.encora.purab.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndPoints = List.of(
      "/s"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest ->
                    openApiEndPoints
                    .stream()
                    .noneMatch(uri->serverHttpRequest.getURI().getPath().contains(uri));

}
