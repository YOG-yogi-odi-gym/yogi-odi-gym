package com.health.yogiodigym.lesson.dto;

import com.health.yogiodigym.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberLatLonDto {
    private Long id;
    private String name;
    private String email;
    private Float latitude;
    private Float longitude;

    public MemberLatLonDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.latitude = member.getLatitude();
        this.longitude = member.getLongitude();
    }
}