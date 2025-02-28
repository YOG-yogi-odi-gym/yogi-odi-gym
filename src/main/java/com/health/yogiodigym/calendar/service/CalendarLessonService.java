package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarLessonDto;

import java.time.LocalDate;
import java.util.List;

public interface CalendarLessonService {

    List<CalendarLessonDto> getLessonsByMemberId(Long memberId);

    List<CalendarLessonDto> getLessonsByMemberAndDate(Long memberId, LocalDate selectedDate);

}
