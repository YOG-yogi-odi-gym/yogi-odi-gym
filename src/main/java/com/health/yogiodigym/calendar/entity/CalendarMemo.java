package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "calendar_memo")
@Getter
@Setter
@NoArgsConstructor
public class CalendarMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 메모 번호 (기본키)

    @Column(nullable = false)
    private String title; // 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String context; // 내용

    @Column(nullable = false)
    private LocalDate date; // 날짜

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 아이디 (외래키)
}