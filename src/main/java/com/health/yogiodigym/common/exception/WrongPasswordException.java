package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.WRONG_PASSWORD_ERROR;

public class WrongPasswordException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return WRONG_PASSWORD_ERROR.getMessage();
    }
}
