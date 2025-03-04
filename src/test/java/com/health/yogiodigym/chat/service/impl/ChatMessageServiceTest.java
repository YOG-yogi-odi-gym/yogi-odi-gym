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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @DisplayName("채팅 메시지 저장 테스트")
    void test() {
        // given
        Member mockMember = Member.builder()
                .id(1L)
                .build();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mockMember));

        ChatRoom mockChatRoom = ChatRoom.builder()
                .id(1L)
                .roomId("test-room")
                .build();
        when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));

        // when
        chatMessageService.saveMessage(messageRequest);

        // then
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    // TODO 예외 테스트

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

        List<ChatMessage> messages = List.of(
                ChatMessage.builder()
                        .id(2L)
                        .member(mockMember)
                        .chatRoom(mockChatRoom)
                        .build(),
                ChatMessage.builder()
                        .id(3L)
                        .member(mockMember)
                        .chatRoom(mockChatRoom)
                        .build(),
                ChatMessage.builder()
                        .id(4L)
                        .member(mockMember)
                        .chatRoom(mockChatRoom)
                        .build()
        );
        when(chatMessageRepository.findByMemberAndChatRoomAndIdGreaterThan(any(Member.class), any(ChatRoom.class), anyLong()))
                .thenReturn(messages);

        // when
        List<MessageResponseDto> unReadMessages = chatMessageService.getUnReadMessages(mockMember, "test-room");

        // then
        assertThat(unReadMessages.size()).isEqualTo(3);
    }
}