package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    FOOD_NOT_FOUND("식단이 존재하지 않습니다."),
    EXERCISE_NOT_FOUND("운동 기록이 존재하지 않습니다."),
    DATA_FOOD_NOT_FOUND("음식 데이터가 존재하지 않습니다."),
    DATA_EXERCISE_NOT_FOUND("운동 데이터가 존재하지 않습니다."),
    MEMO_NOT_FOUND("메모가 존재하지 않습니다."),


    SERVER_ERROR("서버 내부 오류"),
    LESSON_CANCEL_ERROR("수강 내역이 없습니다."),
    LESSON_ENROLLMENT_ERROR("이미 수강 중인 강의입니다."),
    EXISTING_MEMBER_ERROR("이미 존재하는 회원입니다."),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다."),

    FOOD_NOT_FOUND("식단이 존재하지 않습니다."),
    EXERCISE_NOT_FOUND("운동 기록이 존재하지 않습니다."),
    DATA_FOOD_NOT_FOUND("음식 데이터가 존재하지 않습니다."),
    DATA_EXERCISE_NOT_FOUND("운동 데이터가 존재하지 않습니다."),
    MEMO_NOT_FOUND("메모가 존재하지 않습니다."),

    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다."),
    LESSON_NOT_FOUND("강의가 존재하지 않습니다."),
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND("카테고리가 존재하지 않습니다."),
    MEMBER_NOT_IN_CHAT_ROOM("채팅방 참여자가 아닙니다."),
    ALREADY_CHAT_PARTICIPANT("이미 채팅방에 참여중입니다."),
    EXISTING_MEMBER_ERROR("이미 존재하는 회원입니다.");

    private final String message;
}
