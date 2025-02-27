package com.health.yogiodigym.common.exception;

import static com.health.yogiodigym.common.message.ErrorMessage.ALREADY_CHAT_PARTICIPANT;

import org.springframework.http.HttpStatus;

public class AlreadyChatParticipantException extends CustomException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return ALREADY_CHAT_PARTICIPANT.getMessage();
    }

}
