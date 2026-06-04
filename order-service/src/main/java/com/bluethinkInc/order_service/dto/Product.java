package com.bluethinkInc.order_service.dto;




import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public class Product {
    private Long productId;
    private String productName;
    private String productDescription;
    private double price;
    private LocalDateTime mfd;

    public Product(){}
    public Product(Long productId, String productName, double price, String productDescription, LocalDateTime mfd) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.mfd = mfd;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getMfd() {
        return mfd;
    }
}
