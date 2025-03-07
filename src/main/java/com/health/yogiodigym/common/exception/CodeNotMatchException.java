package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.CODE_NOT_MATCH_ERROR;

public class CodeNotMatchException extends CustomException{

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return CODE_NOT_MATCH_ERROR.getMessage();
    }
}
