package com.health.yogiodigym.chat.config;

import com.health.yogiodigym.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final ChatRoomService chatRoomService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            Long senderId = Long.parseLong(accessor.getFirstNativeHeader("senderId"));
            String roomId = accessor.getFirstNativeHeader("roomId");

            log.info(">>> 사용자가 연결되었습니다. {}", senderId);

            chatRoomService.checkParticipant(senderId, roomId);
        }

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info(">>> 채팅방에 입장하였습니다.");
        }

        if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {
            log.info(">>> 채팅방과 연결이 끊겼습니다.");
        }

        return message;
    }

}