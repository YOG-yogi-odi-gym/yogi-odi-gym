package com.health.yogiodigym.chat.repository;

import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByRoomId(String roomId);

    @Query("SELECT cr FROM ChatRoom cr " +
            "JOIN ChatParticipant cp ON cr.id = cp.chatRoom.id " +
            "WHERE cp.member = :member")
    List<ChatRoom> findChatRoomsByMember(@Param("member") Member member);

}
