package com.health.yogiodigym.admin.dto;

import com.health.yogiodigym.my.entity.MemberToMaster;
import lombok.*;

import java.util.Set;

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
        private Set<String> certificate;

        public static MemberToMasterResponseDto from(MemberToMaster memberToMaster) {

            return MemberToMasterResponseDto.builder()
                    .id(memberToMaster.getId())
                    .memberId(memberToMaster.getMember().getId())
                    .memberName(memberToMaster.getMember().getName())
                    .memberEmail(memberToMaster.getMember().getEmail())
                    .certificate(memberToMaster.getCertificate())
                    .build();
        }
    }
}