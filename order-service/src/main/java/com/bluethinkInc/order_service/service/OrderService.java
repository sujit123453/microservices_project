package com.bluethinkInc.order_service.service;

import com.bluethinkInc.order_service.client.PaymentClient;
import com.bluethinkInc.order_service.dto.*;
import com.bluethinkInc.order_service.model.Order;
import com.bluethinkInc.order_service.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final PaymentClient paymentClient; //feign client
    private final OrderProducer orderProducer;
    private final CircuitBreakerService circuitBreakerService;

    public OrderService(OrderRepo orderRepo, CircuitBreakerService circuitBreakerService,
                        PaymentClient paymentClient, OrderProducer orderProducer) {
        this.orderRepo = orderRepo;
        this.paymentClient = paymentClient;
        this.circuitBreakerService = circuitBreakerService;
        this.orderProducer = orderProducer;
    }

    public OrderResponse createOrderService(Order order) {
        order.setOderDate(LocalDateTime.now());

        Order savedOrder = orderRepo.save(order);
        Product product = circuitBreakerService.getProduct(order.getProductId());
        User user = circuitBreakerService.getUser(order.getUserId());
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
        User user = circuitBreakerService.getUser(order.getUserId());
        Product product = circuitBreakerService.getProduct(order.getProductId());
        return new OrderResponse(
                order.getOrderId(),
                user,
                product,
               "Order details:",
                order.getPaymentMethod()
        );
    }
}
