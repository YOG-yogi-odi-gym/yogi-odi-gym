package com.health.yogiodigym.common.exception;

import static com.health.yogiodigym.common.message.ErrorMessage.KICK_INSTRUCTOR_ERROR;

import org.springframework.http.HttpStatus;

public class KickInstructorException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return KICK_INSTRUCTOR_ERROR.getMessage();
    }
}
