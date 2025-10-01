# Product Variant Logic/ Rules

## Product without variant is not allowed
- Every product created should at least have 1 variant (can be called as base variant)

# Microservices Concept / working / Interaction

## Customer - Product Interaction

- Implement authentication on customer-service 
- Authenticated customer can only use "add to cart"

# Calling one service from another

## Using **RestTemplate**
- URL is not hardcoded as microservices can run anywhere

## Another Approach
- Eureka + Feign + Discovery client
- All the services register themselves at Eureka server