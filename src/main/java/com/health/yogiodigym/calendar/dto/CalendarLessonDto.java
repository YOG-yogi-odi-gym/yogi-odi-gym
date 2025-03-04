package com.health.yogiodigym.calendar.dto;

import com.health.yogiodigym.lesson.entity.Lesson;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CalendarLessonDto {
    private LocalDate date;
    private Long id;
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;

    public CalendarLessonDto(LocalDate date, Lesson lesson) {
        this.date = date;
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.startTime = lesson.getStartTime();
        this.endTime = lesson.getEndTime();
    }
}
