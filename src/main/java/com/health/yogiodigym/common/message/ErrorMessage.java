package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    SERVER_ERROR("서버 내부 오류"),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다."),
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다."),
    LESSON_NOT_FOUND("강의가 존재하지 않습니다."),
    MEMBER_NOT_IN_CHAT_ROOM("채팅방 참여자가 아닙니다."),
    ALREADY_CHAT_PARTICIPANT("이미 채팅방에 참여중입니다.");

    private final String message;
}
