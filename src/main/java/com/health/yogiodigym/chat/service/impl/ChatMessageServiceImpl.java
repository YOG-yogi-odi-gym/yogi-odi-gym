package com.health.yogiodigym.chat.service.impl;

import static com.health.yogiodigym.chat.config.ChatConstants.PAGE_SIZE;

import com.health.yogiodigym.chat.dto.MessageDto.MessageRequestDto;
import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import com.health.yogiodigym.chat.entity.ChatMessage;
import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatMessageRepository;
import com.health.yogiodigym.chat.repository.ChatParticipantRepository;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.chat.service.ChatMessageService;
import com.health.yogiodigym.common.exception.ChatRoomNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotInChatRoomException;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    public MessageResponseDto saveMessage(MessageRequestDto messageRequest) {
        Member member = memberRepository.findById(messageRequest.getSenderId())
                .orElseThrow(() -> new MemberNotFoundException(messageRequest.getSenderId()));

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(messageRequest.getRoomId())
                .orElseThrow(() -> new ChatRoomNotFoundException(messageRequest.getRoomId()));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .member(member)
                .content(messageRequest.getMessage())
                .build();
        chatMessageRepository.save(chatMessage);

        return new MessageResponseDto(chatMessage);
    }

    @Override
    public List<MessageResponseDto> getUnReadMessages(Member member, String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        ChatParticipant chatParticipant = chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom)
                .orElseThrow(() -> new MemberNotInChatRoomException(member.getId()));

        List<ChatMessage> unreadMessages =
                chatMessageRepository.findByChatRoomAndIdGreaterThan(chatRoom, chatParticipant.getLastReadMessageId());

        if (!unreadMessages.isEmpty()) {
            chatParticipant.updateLastReadMessageId(unreadMessages.get(unreadMessages.size() - 1).getId());
        } else {
            return new ArrayList<>();
        }

        return unreadMessages.stream()
                .map(MessageResponseDto::new)
                .toList();
    }

    @Override
    public void updateReadStatus(Member member, String roomId, Long lastMessageId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        ChatParticipant chatParticipant = chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom)
                .orElseThrow(() -> new MemberNotInChatRoomException(member.getId()));

        chatParticipant.updateLastReadMessageId(lastMessageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponseDto> getReadMessages(Member member, String roomId, Long lastReadMessageId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        Page<ChatMessage> chatMessages = chatMessageRepository
                .findByChatRoomAndIdLessThanEqualOrderByIdDesc(chatRoom, lastReadMessageId, pageable);

        List<ChatMessage> messages = new ArrayList<>(chatMessages.getContent());
        messages.sort(Comparator.comparing(ChatMessage::getId));

        return messages.stream()
                .map(MessageResponseDto::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getLastMessageId(Member member, String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        ChatParticipant chatParticipant = chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom)
                .orElseThrow(() -> new MemberNotInChatRoomException(member.getId()));

        return chatParticipant.getLastReadMessageId();
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalPage(String roomId, Long lastMessageId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        long totalCount = chatMessageRepository.countByChatRoomAndIdLessThanEqual(chatRoom, lastMessageId);
        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }

}
