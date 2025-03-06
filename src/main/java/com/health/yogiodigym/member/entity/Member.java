package com.health.yogiodigym.member.entity;

import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.status.MemberStatus;
import com.health.yogiodigym.my.dto.UpdateMemberDto;
import com.health.yogiodigym.my.dto.UpdateOAuthMemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

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
}
