package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    GET_CALENDAR_EXERCISE_SUCCESS("유저의 운동정보를 불러오는데 성공하였습니다."),
    GET_ONE_CALENDAR_EXERCISE_SUCCESS("해당날짜의 운동정보를 불러오는데 성공하였습니다."),
    POST_CALENDAR_EXERCISE_SUCCESS("유저의 운동정보를 삽입하는데 성공하였습니다."),
    PUT_CALENDAR_EXERCISE_SUCCESS("유저의 운동정보를 수정하는데 성공하였습니다."),
    DELETE_CALENDAR_EXERCISE_SUCCESS("유저의 운동정보를 삭제하는데 성공하였습니다."),
    GET_CALENDAR_FOOD_SUCCESS("유저의 식단정보를 불러오는데 성공하였습니다."),
    GET_ONE_CALENDAR_FOOD_SUCCESS("해당날짜의 식단정보를 불러오는데 성공하였습니다."),
    POST_CALENDAR_FOOD_SUCCESS("유저의 식단정보를 삽입하는데 성공하였습니다."),
    PUT_CALENDAR_FOOD_SUCCESS("유저의 식단정보를 수정하는데 성공하였습니다."),
    DELETE_CALENDAR_FOOD_SUCCESS("유저의 식단정보를 삭제하는데 성공하였습니다."),
    GET_CALENDAR_MEMO_SUCCESS("유저의 메모를 불러오는데 성공하였습니다."),
    GET_ONE_CALENDAR_MEMO_SUCCESS("해당날짜의 메모를 불러오는데 성공하였습니다."),
    POST_CALENDAR_MEMO_SUCCESS("유저의 메모를 삽입하는데 성공하였습니다."),
    PUT_CALENDAR_MEMO_SUCCESS("유저의 메모를 수정하는데 성공하였습니다."),
    DELETE_CALENDAR_MEMO_SUCCESS("유저의 메모를 삭제하는데 성공하였습니다."),

    REGIST_SUCCESS("회원가입에 성공하였습니다."),
    MEMBER_SEARCH_SUCCESS("회원목록 검색에 성공하였습니다."),
    MEMBER_STATUS_CHANGE_SUCCESS("회원 비활성화 성공하였습니다.");

    private final String message;
}
