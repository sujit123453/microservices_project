package com.bluethinkInc.order_service.dto;

public class PaymentRequest {
    private Long orderId;
    private double amount;
    private String paymentMethod;

    public PaymentRequest() {
    }

    public PaymentRequest(Long orderId, Double amount,String paymentMethod) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }

}
