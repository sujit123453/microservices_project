package com.bluethinkInc.payment_services.service;

import com.bluethinkInc.payment_services.dto.OrderDetails;
import com.bluethinkInc.payment_services.dto.PaymentRequest;
import com.bluethinkInc.payment_services.entity.PaymentResponse;
import com.bluethinkInc.payment_services.model.Payment;
import com.bluethinkInc.payment_services.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepo paymentRepo;
    private final RestTemplate restTemplate;

    public PaymentService(PaymentRepo paymentRepo,RestTemplate restTemplate) {
        this.paymentRepo = paymentRepo;
        this.restTemplate = restTemplate;
    }

    @Value("${order-service.base-url}")
    private String orderServiceUrl;

    public Payment makePayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();

        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());
        // dummy success
        payment.setPaymentStatus("SUCCESS");

        return paymentRepo.save(payment);
    }

    public PaymentResponse getPaymentDetails(Long paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(()->new RuntimeException("Payment not found"));

        OrderDetails orderDetails = restTemplate.getForObject(
                orderServiceUrl + "/orders/" + payment.getOrderId(),
                OrderDetails.class
        );
        return new PaymentResponse(
                payment.getPaymentId(),
                orderDetails
        );
    }
}
