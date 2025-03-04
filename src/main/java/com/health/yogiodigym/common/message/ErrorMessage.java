package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    SERVER_ERROR("서버 내부 오류"),
    LESSON_CANCEL_ERROR("수강 내역이 없습니다."),
    LESSON_ENROLLMENT_ERROR("이미 수강 중인 강의입니다."),
    EXISTING_MEMBER_ERROR("이미 존재하는 회원입니다."),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다."),
    REGIST_MEMBER_ERROR("잘못된 회원가입 정보입니다."),
    BOARD_NOT_FOUND("게시판이 존재하지 않습니다."),
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다."),
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다."),
    LESSON_NOT_FOUND("강의가 존재하지 않습니다."),
    CATEGORY_NOT_FOUND("카테고리가 존재하지 않습니다."),
    MEMBER_NOT_IN_CHAT_ROOM("채팅방 참여자가 아닙니다."),
    ALREADY_CHAT_PARTICIPANT("이미 채팅방에 참여중입니다."),
    NO_DELETE_PERMISSION("삭제 권한이 없습니다.");

    private final String message;
}
