package com.health.yogiodigym.calendar.entity;


import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식단 번호 (기본키)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_food_id", nullable = false)
    private DataFood dataFood; // 음식 데이터 참조 (외래키)

    @Column(nullable = false)
    private String name; // 음식 이름

    @Column(nullable = false)
    private Float hundredGram; // 섭취량 (100g 단위)

    @Column(nullable = false)
    private Float calories; // 음식 칼로리

    @Column(nullable = false)
    private LocalDate date; // 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 아이디 (외래키)
}
