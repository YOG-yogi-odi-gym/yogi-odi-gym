package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.PASSWORD_EMPTY_ERROR;

public class PasswordEmptyException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return PASSWORD_EMPTY_ERROR.getMessage();
    }
}
