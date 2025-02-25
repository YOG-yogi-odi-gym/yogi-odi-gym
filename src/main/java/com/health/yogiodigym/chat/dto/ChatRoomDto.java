package com.health.yogiodigym.chat.dto;

import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.chat.entity.ChatRoom;
import lombok.*;

public class ChatRoomDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatRoomResponseDto {
        private Long id;
        private String roomId;
        private String lessonTitle;
        private boolean isGroupChat;

        public ChatRoomResponseDto(Lesson lesson) {
            ChatRoom chatRoom = lesson.getChatRoom();
            this.id = chatRoom.getId();
            this.roomId = chatRoom.getRoomId();
            this.lessonTitle = lesson.getTitle();
            this.isGroupChat = chatRoom.isGroupChat();
        }
    }
}
