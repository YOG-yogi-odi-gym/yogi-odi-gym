package com.health.yogiodigym.chat.service;

import com.health.yogiodigym.chat.dto.MessageDto.MessageRequestDto;
import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ChatMessageService {
    MessageResponseDto saveMessage(MessageRequestDto messageRequest);

    List<MessageResponseDto> getUnReadMessages(Member member, String roomId);

    void updateReadStatus(Member member, String roomId, Long lastMessageId);

    List<MessageResponseDto> getReadMessages(Member member, String roomId, Long lastMessageId, Pageable pageable);

    Long getLastMessageId(Member member, String roomId);

    int getTotalPage(Member member, String roomId);
}
