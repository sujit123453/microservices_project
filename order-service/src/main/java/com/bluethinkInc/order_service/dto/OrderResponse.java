package com.bluethinkInc.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private User user;
    private Product product;
    private String orderStatus;
    private String paymentRequest;

}
