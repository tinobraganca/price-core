package com.inditex.price_core.infrasctruture.adapter.controller.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
