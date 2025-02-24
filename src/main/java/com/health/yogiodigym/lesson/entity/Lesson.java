package com.health.yogiodigym.lesson.entity;

import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int days;
    private String location;
    private float latitude;
    private float longitude;
    private String detailedLocation;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "start_day", nullable = false)
    private LocalDate startDay;

    @Column(name = "end_day", nullable = false)
    private LocalDate endDay;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer current = 1;
    private Integer max;

    @ManyToOne
    @JoinColumn(name = "master_id", nullable = false)
    private Member master;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime = LocalDateTime.now();
}