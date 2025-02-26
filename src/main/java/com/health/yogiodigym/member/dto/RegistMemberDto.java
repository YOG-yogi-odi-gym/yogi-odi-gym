package com.health.yogiodigym.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistMemberDto {
    private String name;
    private String email;
    private String pwd;
    private String gender;
    private float weight;
    private float height;
    private String addr;
    private float latitude;
    private float longitude;
}
