package com.bluethinkInc.order_service.dto;


public class OrderResponse {
    private Long orderId;
    private User user;
    private Product product;
    private String orderStatus;
    private String paymentRequest;

    public OrderResponse(){}
    public OrderResponse(Long orderId, User user, Product product,String orderStatus,
                          String paymentRequest) {
        this.orderId = orderId;
        this.user = user;
        this.product = product;
        this.paymentRequest = paymentRequest;
        this.orderStatus = orderStatus;
    }
    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentRequest(){
        return paymentRequest;
    }

    public Long getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }
}
