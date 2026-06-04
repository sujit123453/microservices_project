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
    private String paymentMethod;
    private  LocalDateTime orderDate;

    public Order(){}
    public Order(Long orderId, Long userId, Long productId, LocalDateTime orderDate,String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
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

    public String getPaymentMethod(){
        return paymentMethod;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOderDate(LocalDateTime orderDate){
        this.orderDate = orderDate;
    }
}
