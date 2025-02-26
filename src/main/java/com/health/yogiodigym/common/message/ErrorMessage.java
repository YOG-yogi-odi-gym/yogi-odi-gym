package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    SERVER_ERROR("서버 내부 오류"),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다."),
    EXISTING_MEMBER_ERROR("이미 존재하는 회원입니다.");

    private final String message;
}
