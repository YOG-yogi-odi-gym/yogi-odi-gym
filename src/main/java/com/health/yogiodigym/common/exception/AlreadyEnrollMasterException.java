package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.ALREADY_ENROLL_MASTER_ERROR;

public class AlreadyEnrollMasterException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return ALREADY_ENROLL_MASTER_ERROR.getMessage();
    }
}
