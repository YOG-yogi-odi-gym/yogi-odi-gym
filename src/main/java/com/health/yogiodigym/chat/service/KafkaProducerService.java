package com.health.yogiodigym.chat.service;

import static com.health.yogiodigym.chat.config.ChatConstants.CHAT_TOPIC;

import com.health.yogiodigym.chat.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    public void sendMessage(MessageDto message) {
        kafkaTemplate.send(CHAT_TOPIC, message);
    }
}

