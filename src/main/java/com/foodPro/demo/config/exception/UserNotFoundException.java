package com.foodPro.demo.config.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userEmail){
        super(userEmail + " NotFoundException");
    }
}
