package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    REGIST_SUCCESS("회원가입에 성공하였습니다."),
    SEARCH_GYMS_SUCCESS("헬스장 조회에 성공하였습니다."),
    CREATE_CHAT_ROOMS_SUCCESS("채팅방 생성에 성공하였습니다."),
    GET_MY_CHAT_ROOMS_SUCCESS("채팅방 목록 조회에 성공하였습니다."),
    KICK_MEMBER_SUCCESS("회원 강퇴에 성공하였습니다."),
    QUIT_CHAT_ROOM_SUCCESS("채팅방 나가기에 성공하였습니다."),
    ADMIN_MEMBER_SEARCH_SUCCESS("회원목록 검색에 성공하였습니다."),
    ADMIN_MEMBER_STATUS_CHANGE_SUCCESS("회원 비활성화 성공하였습니다."),
    ADMIN_LESSON_SEARCH_SUCCESS("강의목록 검색에 성공하였습니다."),
    ADMIN_LESSON_DELETE_SUCCESS("강의 삭제에 성공하였습니다."),
    ADMIN_BOARD_SEARCH_SUCCESS("게시판 검색에 성공하였습니다.");

    private final String message;
}
