package com.health.yogiodigym.gym.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DataGym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String oldAddress;
    private String streetAddress;
    private float latitude;
    private float longitude;
    private String phoneNum;
    private Integer totalArea;
    private Integer trainers;
    private LocalDate approvalDate;
}