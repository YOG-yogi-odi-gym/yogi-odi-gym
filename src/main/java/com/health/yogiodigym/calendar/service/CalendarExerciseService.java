package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.*;


import java.time.LocalDate;
import java.util.List;

public interface CalendarExerciseService {

    List<CalendarExerciseSelectDto> findByMemberId(Long memberId);

    List<CalendarExerciseSelectDto> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    void postExerciseByDate(CalendarExerciseInsertDto dto);

    void putExerciseByDate(CalendarExerciseUpdateDto dto);

    void deleteExerciseByDate(Long id);
}
