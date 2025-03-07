package com.health.yogiodigym.common.exception;

import static com.health.yogiodigym.common.message.ErrorMessage.FORBIDDEN_ERROR;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getMessage() {
        return FORBIDDEN_ERROR.getMessage();
    }
}
