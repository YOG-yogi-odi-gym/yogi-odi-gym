package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.response.HttpResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    // TODO ExceptionHandler 알아보기!
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        HttpResponseDto responseDto = new HttpResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
