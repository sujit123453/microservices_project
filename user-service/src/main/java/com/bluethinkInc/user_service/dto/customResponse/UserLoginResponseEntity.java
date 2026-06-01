package com.bluethinkInc.user_service.dto.customResponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginResponseEntity<T> {
    private String message;
    private int status;
    private T data;
    private LocalDateTime timeStamp;

    public UserLoginResponseEntity(){}
    public UserLoginResponseEntity(String message,int status,T data){
        this.message = message;
        this.status = status;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }
}
