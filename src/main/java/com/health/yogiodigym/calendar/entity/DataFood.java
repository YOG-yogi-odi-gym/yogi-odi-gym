package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer calories;

    private Float protein;

    private Float fat;

    private Float carbohydrates;
}
