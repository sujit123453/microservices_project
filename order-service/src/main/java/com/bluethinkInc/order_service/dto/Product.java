package com.bluethinkInc.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private String productDescription;
    private double price;
    private LocalDateTime mfd;


}
