package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.EMAILCODE_NOT_FOUND;

public class EmailcodeNotFoundException extends CustomException{

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return EMAILCODE_NOT_FOUND.getMessage();
    }
}
