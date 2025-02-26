package com.health.yogiodigym.chat.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.config.JpaConfig;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Import({JpaConfig.class})
@DataJpaTest
@Transactional
class ChatRoomRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Test
    @DisplayName("채팅방번호로 채팅방 조회")
    void testFindByRoomId() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId("test-room")
                .build();
        chatRoomRepository.save(chatRoom);

        // when
        Optional<ChatRoom> savedChatRoom = chatRoomRepository.findByRoomId(chatRoom.getRoomId());

        // then
        assertThat(savedChatRoom.isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원아이디로 채팅방목록 조회 쿼리메서드 테스트")
    void testFindChatRoomsByMemberId() {
        // given
        Member member = Member.builder()
                .email("email@email.com")
                .name("member")
                .build();
        memberRepository.save(member);

        for (long i = 1; i <= 3; i++) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomId("chat-room-" + i)
                    .isGroupChat(true)
                    .build();
            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

            chatParticipantRepository.save(
                    ChatParticipant.builder()
                            .member(member)
                            .chatRoom(savedChatRoom)
                            .build()
            );
        }

        // when
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByMember(member);

        // then
        assertThat(chatRooms.size()).isEqualTo(3);
        assertThat(chatRooms.get(0).getRoomId()).isEqualTo("chat-room-1");
        assertThat(chatRooms.get(1).getRoomId()).isEqualTo("chat-room-2");
        assertThat(chatRooms.get(2).getRoomId()).isEqualTo("chat-room-3");
    }

}