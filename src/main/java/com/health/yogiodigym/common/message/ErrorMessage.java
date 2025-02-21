package com.health.yogiodigym.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    SERVER_ERROR("서버 내부 오류");

    private final String message;
}
