package com.health.yogiodigym.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long senderId;
    private String roomId;
    private String message;
}
