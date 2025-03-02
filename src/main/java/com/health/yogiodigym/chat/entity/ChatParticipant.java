package com.health.yogiodigym.chat.entity;

import com.health.yogiodigym.common.entity.BaseTimeEntity;
import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatParticipant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    private Long last_read_message_id;

    public void updateLastReadMessageId(Long last_read_message_id) {
        this.last_read_message_id = last_read_message_id;
    }
}
