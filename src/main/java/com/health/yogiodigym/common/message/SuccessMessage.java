package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    JOIN_SUCCESS("회원가입에 성공하였습니다."),
    CREATE_CHAT_ROOMS_SUCCESS("채팅방 생성에 성공하였습니다."),
    GET_MY_CHAT_ROOMS_SUCCESS("채팅방 목록 조회에 성공하였습니다."),
    KICK_MEMBER_SUCCESS("회원 강퇴에 성공하였습니다."),
    QUIT_CHAT_ROOM_SUCCESS("채팅방 나가기에 성공하였습니다.");

    private final String message;
}
