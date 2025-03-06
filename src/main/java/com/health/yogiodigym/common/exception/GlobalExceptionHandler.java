package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.health.yogiodigym.common.message.ErrorMessage.VALID_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> globalExceptionHandler(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new HttpResponse(e.getStatus(), e.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validExceptionHandler(MethodArgumentNotValidException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponse(HttpStatus.BAD_REQUEST,
                VALID_ERROR.getMessage(), e.getBindingResult().getAllErrors()));
    }
}
