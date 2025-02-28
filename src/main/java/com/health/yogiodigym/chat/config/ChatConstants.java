package com.health.yogiodigym.chat.config;

public interface ChatConstants {
    String CHAT_TOPIC = "chat-room";
    String CONNECT_URL = "/ws-connect";
    String PUBLISH_URL = "/publish";
    String TOPIC_URL = "/topic";

    String ENTER_CHAT_ROOM_MESSAGE_PREFIX = "님이 입장했습니다.";
    String QUIT_CHAT_ROOM_MESSAGE_SUFFIX = "님이 채팅방을 나갔습니다.";
}
