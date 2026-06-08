package com.bluethinkInc.order_service.controller;

import com.bluethinkInc.order_service.dto.OrderResponse;
import com.bluethinkInc.order_service.model.Order;
import com.bluethinkInc.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrderController(@RequestBody Order order) {
        try {
           OrderResponse response =  orderService.createOrderService(order);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Order not Successfully:" + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {

        return orderService.getOrderDetails(orderId);
    }
}
