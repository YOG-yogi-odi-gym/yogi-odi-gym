package com.health.yogiodigym.chat.service.impl;

import com.health.yogiodigym.chat.dto.ChatParticipantDto;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatParticipantRepository;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.chat.service.ChatParticipantService;
import com.health.yogiodigym.common.exception.ChatRoomNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotInChatRoomException;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ChatParticipantDto> getChatParticipants(Member member, String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom)
                        .orElseThrow(() -> new MemberNotInChatRoomException(member.getId()));

        return chatParticipantRepository.findAllByChatRoom(chatRoom)
                .stream()
                .map(c -> new ChatParticipantDto(c.getMember()))
                .toList();
    }
}
