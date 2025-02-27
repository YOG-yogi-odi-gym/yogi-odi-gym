package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.LessonDto;
import com.health.yogiodigym.lesson.entity.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface CalendarLessonService {

    //Lesson selectLesson(Long lessonId) ;

    List<LessonDto> getLessonsByMemberId(Long memberId);

    List<LessonDto> getLessonsByMemberAndDate(Long memberId, LocalDate selectedDate);

}
