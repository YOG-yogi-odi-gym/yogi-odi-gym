package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Data_Food")
@Getter
@Setter
@NoArgsConstructor
public class DataFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 음식 번호 (기본키)

    @Column(nullable = false)
    private String name; // 음식명

    @Column(nullable = false)
    private Integer calories; // 칼로리 (100g 당)

    private Float protein; // 단백질 함량

    private Float fat; // 지방 함량

    private Float carbohydrates; // 탄수화물 함량
}
