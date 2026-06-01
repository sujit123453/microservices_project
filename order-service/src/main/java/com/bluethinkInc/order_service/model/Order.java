package com.bluethinkInc.order_service.model;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order-details")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private  Long userId;
    private  Long productId;
    private  LocalDateTime orderDate;

    public Order(){}
    public Order(Long orderId, Long userId, Long productId, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderDate = orderDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOderDate(LocalDateTime orderDate){
        this.orderDate = orderDate;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", orderDate=" + orderDate +
                '}';
    }
}
