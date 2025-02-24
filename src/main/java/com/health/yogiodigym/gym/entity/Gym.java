package com.health.yogiodigym.gym.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "data_gym")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "old_address")
    private String oldAddress;

    @Column(name = "street_address")
    private String streetAddress;

    private float latitude;
    private float longitude;
    private String phoneNum;
    private Integer totalArea;
    private Integer trainers;

    @Column(name = "approval_date")
    private LocalDate approvalDate;
}