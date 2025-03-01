package com.health.yogiodigym.chat.repository;

import com.health.yogiodigym.chat.entity.ChatMessage;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Modifying
    @Query("DELETE FROM ChatMessage cm WHERE cm.chatRoom = :chatRoom")
    void deleteAllByChatRoomInBatch(@Param("chatRoom") ChatRoom chatRoom);

    List<ChatMessage> findByMemberAndChatRoomAndIdGreaterThan(Member member, ChatRoom chatRoom, Long lastReadMessageId);

    Page<ChatMessage> findByMemberAndChatRoomAndIdLessThanEqualOrderByIdDesc(
                            Member member,
                            ChatRoom chatRoom,
                            Long lastReadMessageId,
                            Pageable pageable);

    Long countByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
