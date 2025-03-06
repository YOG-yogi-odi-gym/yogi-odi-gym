package com.health.yogiodigym.common.exception;

import org.springframework.http.HttpStatus;

import static com.health.yogiodigym.common.message.ErrorMessage.SOCIAL_MEMBER_PWD_CHANGE_ERROR;

public class SocialMemberPwdChangeException extends CustomException{
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return SOCIAL_MEMBER_PWD_CHANGE_ERROR.getMessage();
    }
}
