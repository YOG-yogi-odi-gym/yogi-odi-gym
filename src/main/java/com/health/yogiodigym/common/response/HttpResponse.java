package com.health.yogiodigym.common.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private int status;
    private String message;
    private Object data;

    public HttpResponse (HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;

    }
}
