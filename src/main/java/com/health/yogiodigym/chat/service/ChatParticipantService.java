package com.health.yogiodigym.chat.service;

import com.health.yogiodigym.chat.dto.ChatParticipantDto;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;

public interface ChatParticipantService {
    List<ChatParticipantDto> getChatParticipants(Member member, String roomId);
}
