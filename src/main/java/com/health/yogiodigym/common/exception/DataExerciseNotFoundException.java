package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.message.ErrorMessage;
import org.springframework.http.HttpStatus;

public class DataExerciseNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return ErrorMessage.DATA_EXERCISE_NOT_FOUND.getMessage();
    }
}
