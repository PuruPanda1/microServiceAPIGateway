# Issues Faced

## Product Variant Issue (Fixed)
- We have to use product + variant ids in orderItem to properly set the order for different variants of a single product

# Product Variant Logic/ Rules

## Product must have 1+ variants
- Every product created should at least have 1 variant (can be called as base variant)

# Microservices Concept / working / Interaction

## Customer - Order Interaction

- Only share the order-id to the customer after creating order and not the full order entity 

### Working
- Customer sends the customerId and list of productIds, productPrice, discountAmount to order-service
- Order Service does following operations:
  - Calls the **customer-service** for user confirmation
  - Calls **product-service** for availability
  - Saves order in **order DB**
  - Sends **Order created Response** i.e. **#orderId**


> Example Request:

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

> Example Response:

```
{
    "OrderId": 23423
}
```

# Calling one service from another

## Using **RestTemplate**
- URL is not hardcoded as microservices can run anywhere

## Another Approach -- Use Feign Client
- Eureka + Feign + Discovery client
- All the services register themselves at Eureka server

