package com.bluethinkInc.payment_services.entity;

import com.bluethinkInc.payment_services.dto.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long payment_id;
    private OrderDetails orderDetails;
}
