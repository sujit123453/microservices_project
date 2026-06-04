package com.bluethinkInc.order_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private Long productId;
    private Long userId;
    private LocalDateTime orderDate;
    private String paymentMethod;

}
