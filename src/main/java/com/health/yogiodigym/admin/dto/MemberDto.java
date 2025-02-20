package com.health.yogiodigym.admin.dto;

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
        private String name;
        private String email;
        private String gender;

        public static MemberResponseDto from(Member member) {
            return MemberResponseDto.builder()
                    .name(member.getName())
                    .email(member.getEmail())
                    .gender(member.getGender())
                    .build();
        }
    }
}
