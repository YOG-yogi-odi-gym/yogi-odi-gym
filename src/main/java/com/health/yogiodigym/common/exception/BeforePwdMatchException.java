package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.MATCH_BEFORE_PASSWORD_ERROR;

public class BeforePwdMatchException extends CustomException{

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessage() {
        return MATCH_BEFORE_PASSWORD_ERROR.getMessage();
    }
}
