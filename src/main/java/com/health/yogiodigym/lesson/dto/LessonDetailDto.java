package com.health.yogiodigym.lesson.dto;

import com.health.yogiodigym.lesson.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class LessonDetailDto {
    private Long id;
    private String title;
    private Long categoryId;
    private String categoryName;
    private int days;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDay;
    private LocalDate endDay;
    private String location;
    private float latitude;
    private float longitude;
    private String detailedLocation;
    private Long masterId;
    private String masterName;
    private String description;
    private Integer current;
    private Integer max;

    public LessonDetailDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.categoryId = lesson.getCategory().getId();
        this.categoryName = lesson.getCategory().getName();
        this.days = lesson.getDays();
        this.startTime = lesson.getStartTime();
        this.endTime = lesson.getEndTime();
        this.startDay = lesson.getStartDay();
        this.endDay = lesson.getEndDay();
        this.location = lesson.getLocation();
        this.latitude = lesson.getLatitude();
        this.longitude = lesson.getLongitude();
        this.detailedLocation = lesson.getDetailedLocation();
        this.masterId = lesson.getMaster().getId();
        this.masterName = lesson.getMaster().getName();
        this.description = lesson.getDescription();
        this.current = lesson.getCurrent();
        this.max = lesson.getMax();
    }
}
