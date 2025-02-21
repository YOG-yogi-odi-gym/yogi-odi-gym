package com.health.yogiodigym.calendar.entity;

import com.health.yogiodigym.calendar.auth.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

//@Entity
//@Table(name = "Member")
//@Getter
//@Setter
//@NoArgsConstructor
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id; // 회원 번호 (기본키)
//
//    @Column(nullable = false)
//    private String name; // 이름
//
//    @Column(unique = true, nullable = false)
//    private String email; // 아이디 (고유값)
//
//    private String pwd; // 비밀번호
//
//    @Column(length = 10)
//    private String gender; // 성별
//
//    private Float weight; // 몸무게
//
//    private Float height; // 키
//
//    private String addr; // 주소
//
//    private Float latitude; // 위도
//
//    private Float longitude; // 경도
//
//    @Column(name = "join_date")
//    private LocalDate joinDate; // 가입일자
//
//    @Column(name = "drop_date")
//    private LocalDate dropDate; // 탈퇴일자
//
//    @Column(length = 50)
//    private String authority; // 권한 (일반, 강사, 관리자)
//
//    private String profile; // 프로필 사진
//
//    private String status; // 회원 상태 (활성화, 비활성화 등)
//}


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

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

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "drop_date")
    private LocalDate dropDate;

    @Column
    private String profile;

    @Column
    private String status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    @Column(name = "role")
    private Set<Role> roles;
}
