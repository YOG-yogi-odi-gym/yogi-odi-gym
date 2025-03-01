package com.health.yogiodigym.chat.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.health.yogiodigym.chat.entity.ChatMessage;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChatMessageRepositoryTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("채팅방을 사용해서 모든 채팅 메시지 삭제")
    void testDeleteAllByChatRoom() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId("test room")
                .isGroupChat(true)
                .build();
        chatRoomRepository.save(chatRoom);

        ChatMessage message1 = ChatMessage.builder()
                .chatRoom(chatRoom)
                .content("1")
                .build();
        ChatMessage message2 = ChatMessage.builder()
                .chatRoom(chatRoom)
                .content("2")
                .build();
        ChatMessage message3 = ChatMessage.builder()
                .chatRoom(chatRoom)
                .content("3")
                .build();
        chatMessageRepository.save(message1);
        chatMessageRepository.save(message2);
        chatMessageRepository.save(message3);

        // when
        chatMessageRepository.deleteAllByChatRoomInBatch(chatRoom);

        // then
        List<ChatMessage> messages = chatMessageRepository.findAll();
        assertThat(messages.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("안 읽은 메시지 조회")
    void findMessagesAfterLastRead() {
        // given
        Member sender = Member.builder()
                .name("user")
                .email("user@gmail.com")
                .build();
        memberRepository.save(sender);

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId("test room")
                .isGroupChat(true)
                .build();
        chatRoomRepository.save(chatRoom);

        List<ChatMessage> messages = List.of(
            ChatMessage.builder()
                    .member(sender)
                    .chatRoom(chatRoom)
                    .build(),
            ChatMessage.builder()
                    .member(sender)
                    .chatRoom(chatRoom)
                    .build(),
            ChatMessage.builder()
                    .member(sender)
                    .chatRoom(chatRoom)
                    .build()
        );
        chatMessageRepository.saveAll(messages);

        // when
        List<ChatMessage> unreadMessages = chatMessageRepository.findByMemberAndChatRoomAndIdGreaterThan(sender, chatRoom, 1L);

        // then
        assertThat(unreadMessages.size()).isEqualTo(2);
    }
}