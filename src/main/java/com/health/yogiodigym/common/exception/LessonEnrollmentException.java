package com.health.yogiodigym.common.exception;

import com.health.yogiodigym.common.message.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
public class LessonEnrollmentException extends CustomException {

    private String errorCode;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        if (errorCode.equals("CANCEL")) {
            return ErrorMessage.LESSON_CANCEL_ERROR.getMessage();
        }else {
            return ErrorMessage.LESSON_ENROLLMENT_ERROR.getMessage();
        }
    }

}