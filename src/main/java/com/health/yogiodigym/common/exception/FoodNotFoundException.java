package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.message.ErrorMessage;
import org.springframework.http.HttpStatus;

public class FoodNotFoundException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return ErrorMessage.FOOD_NOT_FOUND.getMessage();
    }
}
