package com.health.yogiodigym.chat.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChatParticipantRepositoryTest {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    private Member createMockMember() {
        return Member.builder()
                .name("test member")
                .email("test@email.com")
                .build();
    }

    @Test
    @DisplayName("채팅방으로 채팅참여여부 삭제")
    void testDeleteByChatRoom() {
        // given
        Member member = createMockMember();
        memberRepository.save(member);

        ChatRoom chatRoom = new ChatRoom();
        chatRoomRepository.save(chatRoom);

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .build();
        chatParticipantRepository.save(chatParticipant);

        // when
        chatParticipantRepository.deleteByChatRoom(chatRoom);

        // then
        List<ChatParticipant> chatParticipants = chatParticipantRepository.findAll();
        assertThat(chatParticipants.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("회원과 채팅방으로 채팅참여여부 조회")
    void testFindByMemberAndChatRoom() {
        // given
        Member member = createMockMember();
        memberRepository.save(member);

        ChatRoom chatRoom = new ChatRoom();
        chatRoomRepository.save(chatRoom);

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();
        chatParticipantRepository.save(chatParticipant);

        // when
        Optional<ChatParticipant> savedChatParticipant = chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom);

        // then
        assertThat(savedChatParticipant.isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원과 채팅방으로 채팅참여여부 삭제")
    void testDeleteByMemberAndChatRoom() {
        // given
        Member member = createMockMember();
        memberRepository.save(member);

        ChatRoom chatRoom = new ChatRoom();
        chatRoomRepository.save(chatRoom);

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();
        chatParticipantRepository.save(chatParticipant);

        // when
        chatParticipantRepository.deleteByMemberAndChatRoom(member, chatRoom);

        // then
        Optional<ChatParticipant> savedChatParticipant = chatParticipantRepository.findById(chatParticipant.getId());
        assertThat(savedChatParticipant.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("회원과 채팅방으로 채팅참여여부 존재 확인")
    void testExistsByMemberAndChatRoom() {
        // given
        Member member = createMockMember();
        memberRepository.save(member);

        ChatRoom chatRoom = new ChatRoom();
        chatRoomRepository.save(chatRoom);

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();
        chatParticipantRepository.save(chatParticipant);

        // when
        boolean result = chatParticipantRepository.existsByMemberAndChatRoom(member, chatRoom);

        // then
        assertThat(result).isEqualTo(true);
    }
}