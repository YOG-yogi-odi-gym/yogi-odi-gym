package com.health.yogiodigym.admin.dto;

import com.health.yogiodigym.member.entity.MemberToMaster;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MemberToMasterDto {

    @Setter
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberToMasterResponseDto {
        private Long id;
        private Long memberId;
        private String memberName;
        private String memberEmail;
        private String file;

        public static MemberToMasterResponseDto from(MemberToMaster memberToMaster) {

            return MemberToMasterResponseDto.builder()
                    .id(memberToMaster.getId())
                    .memberId(memberToMaster.getId())
                    .memberName(memberToMaster.getMember().getName())
                    .memberEmail(memberToMaster.getMember().getEmail())
                    .file(memberToMaster.getFile())
                    .build();
        }
    }
}