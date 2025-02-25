package com.health.yogiodigym.chat.repository;

import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    void deleteByChatRoom(ChatRoom chatRoom);

    Optional<ChatParticipant> findByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    void deleteByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    boolean existsByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
