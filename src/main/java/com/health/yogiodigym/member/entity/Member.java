//package com.health.yogiodigym.member.entity;
//
//import com.health.yogiodigym.member.auth.MemberStatus;
//import com.health.yogiodigym.member.auth.Role;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.util.Set;
//
//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column
//    private String pwd;
//
//    @Column(length = 10)
//    private String gender;
//
//    @Column
//    private Float weight;
//
//    @Column
//    private Float height;
//
//    @Column
//    private String addr;
//
//    @Column
//    private Float latitude;
//
//    @Column
//    private Float longitude;
//
//    @Column
//    private LocalDate joinDate;
//
//    @Column
//    private LocalDate dropDate;
//
//    @Column
//    private String profile;
//
//    @Column
//    @Enumerated(EnumType.STRING)
//    private MemberStatus status;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
//    @Column(name = "role")
//    private Set<Role> roles;
//}
