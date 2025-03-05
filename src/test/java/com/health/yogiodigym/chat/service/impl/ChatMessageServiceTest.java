package com.health.yogiodigym.chat.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.health.yogiodigym.chat.dto.MessageDto.MessageRequestDto;
import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import com.health.yogiodigym.chat.entity.ChatMessage;
import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatMessageRepository;
import com.health.yogiodigym.chat.repository.ChatParticipantRepository;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import com.health.yogiodigym.util.TestSecurityConfig;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class ChatMessageServiceTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ChatParticipantRepository chatParticipantRepository;

    @InjectMocks
    private ChatMessageServiceImpl chatMessageService;

    private MessageRequestDto messageRequest;

    @BeforeEach
    void setUp() {
        this.messageRequest = MessageRequestDto.builder()
                .senderId(1L)
                .senderName("user1")
                .roomId("test-room")
                .message("message1")
                .build();
    }

    @Test
    @DisplayName("안읽은 채팅 메시지 목록 조회 테스트")
    void testGetUnReadMessages() {
        // given
        Member mockMember = Member.builder()
                .id(1L)
                .build();

        ChatRoom mockChatRoom = ChatRoom.builder()
                .id(1L)
                .roomId("test-room")
                .build();
        when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .member(mockMember)
                .chatRoom(mockChatRoom)
                .last_read_message_id(2L)
                .build();
        when(chatParticipantRepository.findByMemberAndChatRoom(any(Member.class), any(ChatRoom.class)))
                .thenReturn(Optional.of(chatParticipant));

        ChatMessage message1 = ChatMessage.builder()
                .id(2L)
                .member(mockMember)
                .chatRoom(mockChatRoom)
                .build();
        ChatMessage message2 = ChatMessage.builder()
                .id(3L)
                .member(mockMember)
                .chatRoom(mockChatRoom)
                .build();
        ChatMessage message3 = ChatMessage.builder()
                .id(3L)
                .member(mockMember)
                .chatRoom(mockChatRoom)
                .build();
        List<ChatMessage> messages = Arrays.asList(message1, message2, message3);
        for (ChatMessage message: messages) {
            ReflectionTestUtils.setField(message, "createdDate", LocalDateTime.now());
        }
        when(chatMessageRepository.findByChatRoomAndIdGreaterThan(any(ChatRoom.class), anyLong()))
                .thenReturn(messages);

        // when
        List<MessageResponseDto> unReadMessages = chatMessageService.getUnReadMessages(mockMember, "test-room");

        // then
        assertThat(unReadMessages.size()).isEqualTo(3);
    }
}