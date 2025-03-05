package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.message.ErrorMessage;
import org.springframework.http.HttpStatus;

public class DataFoodNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return ErrorMessage.DATA_FOOD_NOT_FOUND.getMessage();
    }
}
