package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "calendar_exercise")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 운동 번호 (기본키)

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "d_exercise_id", nullable = false)
    private DataExercise dataExercise; // 운동 데이터 참조 (외래키)

    @Column(nullable = false)
    private String name; // 운동명

    @Column(nullable = false)
    private Float time; // 운동 시간

    @Column(nullable = false)
    private Float calories; // 운동 칼로리

    @Column(nullable = false)
    private LocalDate date; // 날짜

    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 아이디 (외래키)

}
