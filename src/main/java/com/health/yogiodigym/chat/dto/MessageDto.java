package com.health.yogiodigym.chat.dto;

import com.health.yogiodigym.chat.entity.ChatMessage;
import java.time.LocalDateTime;
import lombok.*;

public class MessageDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageRequestDto {
        private final String type = "SEND";
        private Long senderId;
        private String senderName;
        private String roomId;
        private String message;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageResponseDto {
        private final String type = "RECEIVE";
        private Long messageId;
        private Long senderId;
        private String senderName;
        private String roomId;
        private String message;
        private LocalDateTime sendDate;

        public MessageResponseDto(ChatMessage chatMessage) {
            this.messageId = chatMessage.getId();
            this.senderId = chatMessage.getMember().getId();
            this.senderName = chatMessage.getMember().getName();
            this.roomId = chatMessage.getChatRoom().getRoomId();
            this.message = chatMessage.getContent();
            this.sendDate = chatMessage.getCreatedDate();
        }
    }
}
