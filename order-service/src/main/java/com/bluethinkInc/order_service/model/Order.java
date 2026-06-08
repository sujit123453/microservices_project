package com.bluethinkInc.order_service.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order-details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private  Long userId;
    private  Long productId;
    private String paymentMethod;
    private  LocalDateTime orderDate;

    public void setOderDate(LocalDateTime now) {
        this.orderDate = now;
    }
}
