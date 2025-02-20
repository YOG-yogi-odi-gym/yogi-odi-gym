package com.health.yogiodigym.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HttpResponseDto {
    private int status;
    private Object data;

    public HttpResponseDto(int status, Object data) {
        this.status = status;
        this.data = data;
    }
}
