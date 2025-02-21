package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Data_Exercise")
@Getter
@Setter
@NoArgsConstructor
public class DataExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 운동 번호 (기본키)

    @Column(nullable = false)
    private String name; // 운동명

    @Column(nullable = false, name = "energy_consumption")
    private Float energyConsumption; // 단위체중당 에너지 소비량 (1분 * 체중 * 칼로리)
}