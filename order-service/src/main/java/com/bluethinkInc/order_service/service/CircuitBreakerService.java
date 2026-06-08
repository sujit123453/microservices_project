package com.bluethinkInc.order_service.service;

import com.bluethinkInc.order_service.dto.Product;
import com.bluethinkInc.order_service.dto.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CircuitBreakerService {
    private final RestTemplate restTemplate;
    public CircuitBreakerService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${user-service.base-url}")
    private String userServiceUrl;

    @Value("${product-service.base-url}")
    private String productServiceUrl;

    @CircuitBreaker(name = "userBreaker", fallbackMethod = "getUserFallback")
    public User getUser(Long userId){
      return  restTemplate.getForObject(
              userServiceUrl + "/user/" + userId,
              User.class
      );
    }

    public User getUserFallback(Long userId, Exception ex){
        log.error("getUserFallback Called: " + ex.getMessage());
        User user = new User();
        user.setId(userId);
        user.setName("Unknown user");
        return user;
    }

    @CircuitBreaker(name = "productBreaker", fallbackMethod = "getProductFallback")
    public Product getProduct(Long productId){
        return restTemplate.getForObject(
                productServiceUrl + "/product/" + productId,
                Product.class
        );
    }
    public Product getProductFallback(Long productId, Exception ex){
        log.error("getProductFallback Called: " + ex.getMessage());

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Unknown product");
        return product;
    }
}
