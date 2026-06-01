package com.bluethinkInc.order_service.service;

import com.bluethinkInc.order_service.client.PaymentClient;
import com.bluethinkInc.order_service.dto.OrderResponse;
import com.bluethinkInc.order_service.dto.PaymentRequest;
import com.bluethinkInc.order_service.dto.Product;
import com.bluethinkInc.order_service.dto.User;
import com.bluethinkInc.order_service.model.Order;
import com.bluethinkInc.order_service.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final RestTemplate restTemplate;
    private final PaymentClient paymentClient;

    public OrderService(OrderRepo orderRepo, RestTemplate restTemplate, PaymentClient paymentClient) {
        this.orderRepo = orderRepo;
        this.restTemplate = restTemplate;
        this.paymentClient = paymentClient;
    }

    @Value("${user-service.base-url}")
    private String userServiceUrl;

    @Value("${product-service.base-url}")
    private String productServiceUrl;

    public String createOrderService(Order order) {
        order.setOderDate(LocalDateTime.now());

        Order savedOrder = orderRepo.save(order);

        PaymentRequest paymentRequest =
                new PaymentRequest(
                        savedOrder.getOrderId(),
                        5000.0,
                        "PHONEPAY"
                );

        ResponseEntity<String> paymentResponse =
                paymentClient.makePayment(paymentRequest);

        return "Order confirm successfully :: "
                + paymentResponse.getBody();

    }

    public OrderResponse getOrderDetails(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User user = restTemplate.getForObject(
                userServiceUrl + "/user/" + order.getUserId(),
                User.class
        );
        Product product = restTemplate.getForObject(
                productServiceUrl + "/product/" + order.getProductId(),
                Product.class
        );

        return new OrderResponse(
                order.getOrderId(),
                user,
                product
        );
    }
}
