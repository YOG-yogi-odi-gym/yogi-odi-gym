package com.health.yogiodigym.my.entity;

import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.status.EnrollMasterStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberToMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDate enrollDate;

    @Enumerated(EnumType.STRING)
    private EnrollMasterStatus approvalStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "certificate", joinColumns = @JoinColumn(name = "enroll_id", referencedColumnName = "id"))
    @Column(name = "file_name")
    private Set<String> certificate;
}
