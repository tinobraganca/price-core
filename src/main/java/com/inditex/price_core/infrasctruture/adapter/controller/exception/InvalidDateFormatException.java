package com.inditex.price_core.infrasctruture.adapter.controller.exception;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException(String message){
        super(message);
    }
}
