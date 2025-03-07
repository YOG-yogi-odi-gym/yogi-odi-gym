package com.health.yogiodigym.chat.service;

import com.health.yogiodigym.chat.config.ChatConstants;
import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = ChatConstants.CHAT_TOPIC, groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    public void listen(MessageResponseDto message) {

        log.info("수신한 메시지: {}", message);

        messagingTemplate.convertAndSend("/topic/" + message.getRoomId(), message);
    }

}
