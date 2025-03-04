package com.health.yogiodigym.board.dto;

import com.health.yogiodigym.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberBoardDto {
    private Long id;
    private String name;
    private String email;

    public MemberBoardDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
