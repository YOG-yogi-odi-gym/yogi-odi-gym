package com.health.yogiodigym.lesson.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonEditDto {
    private Long id;
    private String title;
    private Long categoryId;
    private int[] bitDays;
    private String location;
    private String detailedLocation;
    private float latitude;
    private float longitude;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDay;
    private LocalDate endDay;
    private Integer max;
    private String description;

    public int getDays() {
        int bitmask = 0;
        for (int days : bitDays) {
            bitmask |= days;
        }
        return bitmask;
    }
}