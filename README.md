# Java Spring Boot + Microservices Project + API Gateway

This project demonstrates a Java Spring Boot microservices architecture with service discovery, API Gateway, and inter-service communication.

## Architecture Diagram

![Microservices Architecture](./assets/microservices_architecture.png)

## Micro-Services

- customer-service – manages customer data

- product-service – manages products and their variants

- order-service – handles orders, interacts with customer and product services

## Infrastructure

- Eureka-server – service registry for discovery

- Api-gateway – single entry point for routing to microservices

High-Level Flow

- A client makes a request to API Gateway.

- Gateway routes the request to the appropriate service (customer, product, or order).

- Services discover each other through Eureka Server.

- Inter-service communication is done via RestTemplate or Feign Client (with Eureka).

## Customer <--> Order Interaction

1. A customer creates an order by sending customerId and a list of order items.

2. Order Service recieves the customer request

3. Calls customer-service to validate customer.

4. Calls product-service to check availability.

5. Persists order into the order database.
   
7. Sends the product request to update product stock.

8. Returns only the OrderId (not the full order entity).

Example Request
```
{
  "customerId": 2,
  "orderItemList": [
    {
      "productId": 3,
      "variantId": 1,
      "productQuantity": 1,
      "productPrice": 2500.0,
      "discountAmount": 500.0
    },
    {
      "productId": 3,
      "variantId": 2,
      "productQuantity": 1,
      "productPrice": 2500.0,
      "discountAmount": 500.0
    }
  ]
}
```
Example Response
```
{
  "OrderId": 23423
}
```

## Product Variant Logic

- Every product must have at least one variant (base variant).

- Orders reference both productId and variantId to correctly track ordered items.

## Inter-Service Communication

- RestTemplate – used without hardcoding URLs (services discovered via Eureka).

- Feign Client (Used and Recommended) – integrates with Eureka + Discovery Client for clean service-to-service calls.

## Issues Faced

### Product Variant Issue (Fixed)

> Initially, orders could not differentiate between variants of the same product.

Solution: Store both productId + variantId in an orderItem.

## Tech Stack

- Spring Boot (Microservices, REST APIs)

- Spring Cloud Netflix Eureka (Service Discovery)

- Spring Cloud Gateway (API Gateway)

- Feign Client (Inter-service communication)

- MySQL (Database)

## Future Improvements

- Add circuit breaker for fault tolerance.

- Introduce centralized config service.

- Secure services with Spring Security + JWT.
