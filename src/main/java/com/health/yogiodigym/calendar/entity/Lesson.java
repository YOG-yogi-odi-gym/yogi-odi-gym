package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "lesson")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 강의 번호 (기본키)

    @Column(nullable = false)
    private String title; // 강의 제목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 카테고리 (외래키)

    @Column(nullable = false)
    private int days; // 강의요일

    private String location; // 위치

    private Float latitude; // 위도

    private Float longitude; // 경도

    @Column(name="detailed_location")
    private String detailedLocation; // 상세 위치

    @Column(nullable = false, name="start_time")
    private LocalTime startTime; // 강의 시작 시간

    @Column(nullable = false, name="end_time")
    private LocalTime endTime; // 강의 종료 시간

    @Column(nullable = false, name="start_day")
    private LocalDate startDay; // 강의 시작 일

    @Column(nullable = false, name="end_day")
    private LocalDate endDay; // 강의 종료 일

    @Column(columnDefinition = "TEXT")
    private String description; // 강의 소개

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int current; // 현재 수강 인원

    @Column(nullable = false)
    private int max; // 강의 정원 (최대 인원)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    private Member master; // 강사 (회원 참조)

    @Column(name="create_date_time")
    private LocalDateTime createDateTime; // 생성 날짜
}
