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
public class ChatReadStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatMessage chatMessage;

}
