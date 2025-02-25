package com.health.yogiodigym.lesson.dto;

import com.health.yogiodigym.lesson.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String categoryName;
    private Long masterId;
    private String masterName;
    private int days;
    private String location;
    private float latitude;
    private float longitude;
    private String startTime;
    private String endTime;
    private Integer current;
    private Integer max;

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.categoryName = lesson.getCategory().getName();
        this.masterId = lesson.getMaster().getId();
        this.masterName = lesson.getMaster().getName();
        this.days = lesson.getDays();
        this.location = lesson.getLocation();
        this.latitude = lesson.getLatitude();
        this.longitude = lesson.getLongitude();
        this.startTime = lesson.getStartTime().toString();
        this.endTime = lesson.getEndTime().toString();
        this.current = lesson.getCurrent();
        this.max = lesson.getMax();
    }
}
