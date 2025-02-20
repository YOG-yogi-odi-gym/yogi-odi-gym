package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
