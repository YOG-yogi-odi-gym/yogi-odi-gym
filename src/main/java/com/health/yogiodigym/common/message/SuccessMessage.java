package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    JOIN_SUCCESS("회원가입에 성공하였습니다."),
    SEARCH_GYMS_SUCCESS("헬스장 조회에 성공하였습니다.");

    private final String message;
}
