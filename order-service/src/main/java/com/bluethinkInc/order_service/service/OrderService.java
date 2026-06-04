package com.bluethinkInc.order_service.service;

import com.bluethinkInc.order_service.client.PaymentClient;
import com.bluethinkInc.order_service.dto.*;
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
    private final PaymentClient paymentClient; //feign client
    private final OrderProducer orderProducer;

    public OrderService(OrderRepo orderRepo, RestTemplate restTemplate,
                        PaymentClient paymentClient, OrderProducer orderProducer) {
        this.orderRepo = orderRepo;
        this.restTemplate = restTemplate;
        this.paymentClient = paymentClient;
        this.orderProducer = orderProducer;
    }

    @Value("${user-service.base-url}")
    private String userServiceUrl;

    @Value("${product-service.base-url}")
    private String productServiceUrl;


    public OrderResponse createOrderService(Order order) {
        order.setOderDate(LocalDateTime.now());

        Order savedOrder = orderRepo.save(order);
        Product product = restTemplate.getForObject(
                productServiceUrl + "/product/" + savedOrder.getProductId(),
                Product.class
        );
        User user = restTemplate.getForObject(
                userServiceUrl + "/user/internal/" + savedOrder.getUserId(),
                User.class
        );
        if(product == null){
            throw new RuntimeException("Product not found with id " + order.getProductId());
        }
//        PaymentRequest paymentRequest =
//                new PaymentRequest(
//                        savedOrder.getOrderId(),
//                        product.getPrice(),
//                        order.getPaymentMethod()
//                );
//        ResponseEntity<String
//                > paymentResponse =
//                paymentClient.makePayment(paymentRequest);
        //Kafka implementation: order event publish to topic
        OrderEvent event = new OrderEvent(
                savedOrder.getOrderId(),
                savedOrder.getProductId(),
                savedOrder.getUserId(),
                savedOrder.getOrderDate(),
                savedOrder.getPaymentMethod()
        );
        orderProducer.publishOrderEvent(event);

        return new OrderResponse(
              savedOrder.getOrderId(),
                user,
                product,
                "Order created successfully",
//                paymentResponse.getBody()
                "payment request send to Kafka"
        );
    }

    public OrderResponse getOrderDetails(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        User user = restTemplate.getForObject(
                userServiceUrl + "/user/internal/" + order.getUserId(),
                User.class
        );
        Product product = restTemplate.getForObject(
                productServiceUrl + "/product/" + order.getProductId(),
                Product.class
        );

        return new OrderResponse(
                order.getOrderId(),
                user,
                product,
               "Order details:",
                order.getPaymentMethod()
        );
    }
}
