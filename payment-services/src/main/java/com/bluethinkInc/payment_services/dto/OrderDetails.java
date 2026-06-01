package com.bluethinkInc.payment_services.dto;

import com.bluethinkInc.payment_services.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private Long orderId;
    private Payment payment;
    private Product product;
    private User user;
}
