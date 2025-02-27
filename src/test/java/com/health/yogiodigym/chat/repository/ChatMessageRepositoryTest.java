package com.health.yogiodigym.chat.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.health.yogiodigym.chat.entity.ChatMessage;
import com.health.yogiodigym.chat.entity.ChatRoom;
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
}