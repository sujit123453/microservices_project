package com.bluethinkInc.order_service.dto;



import java.time.LocalDateTime;


public class Product {
    private Long productId;
    private String productName;
    private String productDescription;
    private String price;
    private LocalDateTime mfd;

    public Product(){}
    public Product(Long productId, String productName, String price, String productDescription, LocalDateTime mfd) {
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

    public String getPrice() {
        return price;
    }

    public LocalDateTime getMfd() {
        return mfd;
    }
}
