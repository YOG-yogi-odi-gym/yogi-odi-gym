package com.health.yogiodigym.chat.controller.rest;

import com.health.yogiodigym.chat.config.ChatConstants;
import com.health.yogiodigym.chat.dto.MessageDto;
import com.health.yogiodigym.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StompController {

    private final KafkaProducerService kafkaProducerService;

    @MessageMapping("/{roomId}")
    public MessageDto sendMessage(MessageDto message) {

        log.info("전송한 메시지 : {}", message);

        kafkaProducerService.sendMessage(ChatConstants.CHAT_TOPIC, message);

        return message;
    }

}