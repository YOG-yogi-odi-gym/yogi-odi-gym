package com.health.yogiodigym.chat.dto;

import com.health.yogiodigym.chat.entity.ChatMessage;
import java.time.format.DateTimeFormatter;
import lombok.*;

public class MessageDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageRequestDto {
        private final boolean isSend = true;
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
        private final boolean isSend = false;
        private Long messageId;
        private Long senderId;
        private String senderName;
        private String roomId;
        private String message;
        private String profileUrl;
        private String sendDate;

        public MessageResponseDto(ChatMessage chatMessage) {
            this.messageId = chatMessage.getId();
            this.senderId = chatMessage.getMember().getId();
            this.senderName = chatMessage.getMember().getName();
            this.roomId = chatMessage.getChatRoom().getRoomId();
            this.message = chatMessage.getContent();
            this.profileUrl = chatMessage.getMember().getProfile();
            this.sendDate = chatMessage.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }
}
