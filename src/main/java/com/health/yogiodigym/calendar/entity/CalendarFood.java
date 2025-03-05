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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_food_id", nullable = false)
    private DataFood dataFood;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float hundredGram;

    @Column(nullable = false)
    private Float calories;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
