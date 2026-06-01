package com.bluethinkInc.payment_services.controller;

import com.bluethinkInc.payment_services.dto.PaymentRequest;
import com.bluethinkInc.payment_services.entity.PaymentResponse;
import com.bluethinkInc.payment_services.model.Payment;
import com.bluethinkInc.payment_services.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody
                                           PaymentRequest paymentRequest){
        return ResponseEntity.ok(
                paymentService.makePayment(paymentRequest)
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable Long paymentId){
        return ResponseEntity.ok(
                paymentService.getPaymentDetails(paymentId)
        );
    }

}
