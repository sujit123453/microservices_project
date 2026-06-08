package com.bluethinkInc.order_service.client;

import com.bluethinkInc.order_service.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="PAYMENT-SERVICES")
public interface PaymentClient {
    @PostMapping("/payment/pay")
     ResponseEntity<String> makePayment(
            @RequestBody PaymentRequest paymentRequest
            );
}
