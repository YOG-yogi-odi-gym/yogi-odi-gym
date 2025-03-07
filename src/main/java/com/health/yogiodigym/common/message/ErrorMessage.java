package com.health.yogiodigym.common.message;

import com.health.yogiodigym.common.exception.CodeNotMatchException;
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
    VALID_ERROR("잘못된 입력 정보입니다."),
    LESSON_NOT_FOUND("강의가 존재하지 않습니다."),
    BOARD_NOT_FOUND("게시판이 존재하지 않습니다."),
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다."),
    CHAT_ROOM_NOT_FOUND("채팅방이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND("카테고리가 존재하지 않습니다."),
    MEMBER_NOT_IN_CHAT_ROOM("채팅방 참여자가 아닙니다."),
    MEMBER_NOT_IN_LESSON("강의 참여자가 아닙니다."),
    KICK_INSTRUCTOR_ERROR("강의의 강사는 강퇴할 수 없습니다."),
    ALREADY_CHAT_PARTICIPANT("이미 채팅방에 참여중입니다."),
    WRONG_PASSWORD_ERROR("잘못된 비밀번호입니다."),
    NO_DELETE_PERMISSION("삭제 권한이 없습니다."),
    SEND_MAIL_FAIL_ERROR("코드메일 전송에 실패했습니다."),
    EMAILCODE_NOT_FOUND("인증코드를 찾지 못했습니다"),
    CODE_NOT_MATCH_ERROR("인증코드가 일치하지 않습니다."),
    SOCIAL_MEMBER_PWD_CHANGE_ERROR("소셜회원은 비밀번호 변경을 하실 수 없습니다."),
    PASSWORD_EMPTY_ERROR("비밀번호가 비어있습니다."),
    FORBIDDEN_ERROR("권한이 없습니다."),
    ALREADY_ENROLL_MASTER_ERROR("이미 강사신청에 완료되셨습니다.");

    private final String message;
}
