package com.health.yogiodigym.admin.dto;

import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.entity.Member;
import lombok.*;

public class MemberDto {

    @Setter
    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberResponseDto {
        private Long id;
        private String name;
        private String email;
        private MemberStatus status;

        public static MemberResponseDto from(Member member) {
            return MemberResponseDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .status(member.getStatus())
                    .build();
        }
    }
}
