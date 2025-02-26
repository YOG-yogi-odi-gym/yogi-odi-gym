package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    JOIN_SUCCESS("회원가입에 성공하였습니다."),
    MEMBER_SEARCH_SUCCESS("회원목록 검색에 성공하였습니다."),
    MEMBER_STATUS_CHANGE_SUCCESS("회원 비활성화 성공하였습니다.");

    private final String message;
}
