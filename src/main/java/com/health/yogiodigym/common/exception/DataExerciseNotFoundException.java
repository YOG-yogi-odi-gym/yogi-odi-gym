package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

public class DataExerciseNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "DataExercise가 존재하지 않습니다!";
    }
}
