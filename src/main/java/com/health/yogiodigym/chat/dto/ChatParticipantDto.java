package com.health.yogiodigym.chat.dto;

import com.health.yogiodigym.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatParticipantDto {
    private Long memberId;
    private Long instructorId;
    private String memberName;
    private String profileUrl;

    public ChatParticipantDto(Member member, Long instructorId) {
        this.memberId = member.getId();
        this.instructorId = instructorId;
        this.memberName = member.getName();
        this.profileUrl = member.getProfile();
    }
}
