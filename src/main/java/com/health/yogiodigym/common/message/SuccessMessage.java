package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    GET_CALENDAR_LESSON_SUCCESS("유저의 강의정보를 불러오는데 성공하였습니다."),
    GET_ONE_CALENDAR_LESSON_SUCCESS("해당날짜의 강의정보를 불러오는데 성공하였습니다."),
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
    GET_DATA_EXERCISE_SUCCESS("운동 데이터를 불러오는데 성공했습니다."),
    GET_ONE_DATA_EXERCISE_SUCCESS("해당 운동 정보를 불러오는데 성공했습니다."),
    GET_DATA_FOOD_SUCCESS("운동 데이터를 불러오는데 성공했습니다."),
    GET_ONE_DATA_FOOD_SUCCESS("해당 운동 정보를 불러오는데 성공했습니다."),

    REGIST_SUCCESS("회원가입에 성공하였습니다."),
    SEARCH_BOARD_SUCCESS("게시판 조회에 성공하였습니다."),
    SEARCH_LESSON_SUCCESS("강의목록 조회에 성공하였습니다."),
    SEARCH_GYMS_SUCCESS("헬스장 조회에 성공하였습니다."),
    CREATE_CHAT_ROOMS_SUCCESS("채팅방 생성에 성공하였습니다."),
    GET_MY_CHAT_ROOMS_SUCCESS("채팅방 목록 조회에 성공하였습니다."),
    KICK_MEMBER_SUCCESS("회원 강퇴에 성공하였습니다."),
    QUIT_CHAT_ROOM_SUCCESS("채팅방 나가기에 성공하였습니다."),
    ADMIN_MEMBER_SEARCH_SUCCESS("회원목록 검색에 성공하였습니다."),
    ADMIN_MEMBER_STATUS_CHANGE_SUCCESS("회원 비활성화 성공하였습니다."),
    ADMIN_LESSON_SEARCH_SUCCESS("강의목록 검색에 성공하였습니다."),
    ADMIN_LESSON_DELETE_SUCCESS("강의 삭제에 성공하였습니다."),
    WITHDRAWAL_SUCCESS("회원탈퇴 처리되었습니다"),
    PROFILE_UPDATE_SUCCESS("프로필 이미지 저장에 성공하였습니다."),
    MEMBER_UPDATE_SUCCESS("회원정보 수정에 성공하였습니다."),
    ENROLL_MASTER_SUCCESS("강사 신청에 성공하였습니다."),
    ADMIN_BOARD_SEARCH_SUCCESS("게시판 검색에 성공하였습니다."),
    ADMIN_BOARD_DELETE_SUCCESS("게시판 삭제에 성공하였습니다."),
    ADMIN_CATEGORY_DELETE_SUCCESS("카테고리 삭제에 성공하였습니다."),
    ADMIN_CATEGORY_INSERT_SUCCESS("카테고리 추가에 성공하였습니다."),
    ADMIN_CATEGORY_UPDATE_SUCCESS("카테고리 수정에 성공하였습니다."),
    PASSWORD_MATCH_SUCCESS("비밀번호 인증에 성공하였습니다."),
    GET_READ_MESSAGES_SUCCESS("읽은 메시지 조회에 성공하였습니다."),
    UPDATE_LAST_READ_MESSAGE_SUCCESS("마지막에 읽은 메시지 업데이트에 성공하였습니다."),
    ADMIN_AUTHORITY_ADD_SUCCESS("권한 추가에 성공하였습니다."),
    ADMIN_AUTHORITY_REJECT_SUCCESS("권한 반려에 성공하였습니다."),
    SEND_MAILCODE_SUCCESS("인증메일 전송에 성공하였습니다."),
    MAIL_VERIFY_SUCCESS("메일인증에 성공하셨습니다."),
    PASSWORD_CHANGE_SUCCESS("비밀번호 변경에 성공하였습니다.");

    private final String message;
}
