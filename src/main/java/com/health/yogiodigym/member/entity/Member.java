package com.health.yogiodigym.member.entity;

import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.member.status.MemberStatus;
import com.health.yogiodigym.my.dto.UpdateMemberDto;
import com.health.yogiodigym.my.dto.UpdateOAuthMemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

import static com.health.yogiodigym.member.auth.Role.ROLE_USER;
import static com.health.yogiodigym.member.status.MemberStatus.ACTIVE;
import static com.health.yogiodigym.member.status.MemberStatus.INACTIVE;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column
    private String pwd;

    @Column(length = 10)
    private String gender;

    @Column
    private Float weight;

    @Column
    private Float height;

    @Column
    private String addr;

    @Column
    private Float latitude;

    @Column
    private Float longitude;

    @Column
    private LocalDate joinDate;

    @Column
    private LocalDate dropDate;

    @Setter
    @Column
    private String profile;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    @Column(name = "role")
    private Set<Role> roles;

    public Member(RegistMemberDto registMemberDto, String saveFileURL) {
        this.name = registMemberDto.getName();
        this.email = registMemberDto.getEmail();
        this.pwd = registMemberDto.getPwd();
        this.gender = registMemberDto.getGender();
        this.weight = registMemberDto.getWeight();
        this.height = registMemberDto.getHeight();
        this.addr = registMemberDto.getAddr();
        this.latitude = registMemberDto.getLatitude();
        this.longitude = registMemberDto.getLongitude();
        this.joinDate = LocalDate.now();
        this.profile = saveFileURL;
        this.status = ACTIVE;
        this.roles = EnumSet.of(ROLE_USER);
    }

    public void updateMember(UpdateMemberDto updateMemberDto) {
        this.name = updateMemberDto.getName();
        this.pwd = updateMemberDto.getPwd();
        this.weight = updateMemberDto.getWeight();
        this.height = updateMemberDto.getHeight();
        this.addr = updateMemberDto.getAddr();
        this.longitude = updateMemberDto.getLongitude();
        this.latitude = updateMemberDto.getLatitude();
    }

    public void updateOAuthMember(UpdateOAuthMemberDto updateOAuthMemberDto) {
        this.name = updateOAuthMemberDto.getName();
        this.weight = updateOAuthMemberDto.getWeight();
        this.height = updateOAuthMemberDto.getHeight();
        this.addr = updateOAuthMemberDto.getAddr();
        this.longitude = updateOAuthMemberDto.getLongitude();
        this.latitude = updateOAuthMemberDto.getLatitude();
    }

    public void setInactive() {
        this.status = INACTIVE;
        this.dropDate = LocalDate.now();
    }

    public static Member buildRegistMember(RegistMemberDto registMemberDto, String saveFileURL) {
        return Member.builder()
                .name(registMemberDto.getName())
                .email(registMemberDto.getEmail())
                .pwd(registMemberDto.getPwd())
                .gender(registMemberDto.getGender())
                .weight(registMemberDto.getWeight())
                .height(registMemberDto.getHeight())
                .addr(registMemberDto.getAddr())
                .latitude(registMemberDto.getLatitude())
                .longitude(registMemberDto.getLongitude())
                .profile(saveFileURL)
                .joinDate(LocalDate.now())
                .status(ACTIVE)
                .roles(EnumSet.of(Role.ROLE_USER))
                .build();
    }

    public static Member buildRegistOAuthMember(RegistOAuthMemberDto registOAuthMemberDto, String saveFileURL) {
        return Member.builder()
                .name(registOAuthMemberDto.getName())
                .email(registOAuthMemberDto.getEmail())
                .gender(registOAuthMemberDto.getGender())
                .weight(registOAuthMemberDto.getWeight())
                .height(registOAuthMemberDto.getHeight())
                .addr(registOAuthMemberDto.getAddr())
                .latitude(registOAuthMemberDto.getLatitude())
                .longitude(registOAuthMemberDto.getLongitude())
                .profile(saveFileURL)
                .joinDate(LocalDate.now())
                .status(ACTIVE)
                .roles(EnumSet.of(Role.ROLE_USER))
                .build();
    }
}
