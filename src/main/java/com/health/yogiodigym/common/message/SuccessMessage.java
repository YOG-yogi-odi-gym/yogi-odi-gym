package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    REGIST_SUCCESS("회원가입에 성공하였습니다.");

    private final String message;
}
