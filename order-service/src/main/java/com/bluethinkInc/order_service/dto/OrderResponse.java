package com.bluethinkInc.order_service.dto;


public class OrderResponse {
    private Long orderId;
    private User user;
    private Product product;

    public OrderResponse(){}
    public OrderResponse(Long orderId, User user, Product product) {
        this.orderId = orderId;
        this.user = user;
        this.product = product;
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
