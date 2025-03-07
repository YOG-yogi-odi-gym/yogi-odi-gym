package com.health.yogiodigym.common.exception;

import static com.health.yogiodigym.common.message.ErrorMessage.LESSON_NOT_FOUND;

import org.springframework.http.HttpStatus;

public class MemberNotInLessonException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return LESSON_NOT_FOUND.getMessage();
    }
}
