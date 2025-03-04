package com.health.yogiodigym.admin.dto;

import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.status.MemberStatus;
import lombok.*;

import java.util.Set;

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
        private Set<Role> roles;

        public static MemberResponseDto from(Member member) {
            return MemberResponseDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .status(member.getStatus())
                    .roles(member.getRoles())
                    .build();
        }
    }
}
