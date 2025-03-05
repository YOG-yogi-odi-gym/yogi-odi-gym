package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.SEND_MAIL_FAIL_ERROR;

public class SendCodeToMailException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return SEND_MAIL_FAIL_ERROR.getMessage();
    }
}
