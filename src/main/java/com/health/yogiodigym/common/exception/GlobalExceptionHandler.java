package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.response.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> globalExceptionHandler(CustomException e) {
        return ResponseEntity
                .ok()
                .body(new HttpResponse(e.getStatus(), e.getMessage(), null));
    }
}
