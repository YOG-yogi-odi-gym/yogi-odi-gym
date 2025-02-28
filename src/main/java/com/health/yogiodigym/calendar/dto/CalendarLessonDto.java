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
    private LocalDate date;      // 뽑아낸 날짜
    private Long id;       // 강의 번호
    private String title;        // 강의 제목
    private LocalTime startTime; // 강의 시작 시간
    private LocalTime endTime;   // 강의 종료 시간

    public CalendarLessonDto(LocalDate date, Lesson lesson) {
        this.date = date;
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.startTime = lesson.getStartTime();
        this.endTime = lesson.getEndTime();
    }
}
