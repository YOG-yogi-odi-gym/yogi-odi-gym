package com.health.yogiodigym.lesson.dto;

import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLatLonDto {

    private Long id;
    private String name;
    private String email;
    private Float latitude;
    private Float longitude;
    private Set<Role> roles;

    public MemberLatLonDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.latitude = member.getLatitude();
        this.longitude = member.getLongitude();
        this.roles = member.getRoles();
    }
}