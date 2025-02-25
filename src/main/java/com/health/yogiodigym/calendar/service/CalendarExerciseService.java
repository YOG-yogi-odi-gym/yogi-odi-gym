package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.UpdateRequest;
import com.health.yogiodigym.calendar.entity.CalendarExercise;


import java.time.LocalDate;
import java.util.List;

public interface CalendarExerciseService {

    List<CalendarExercise> findByMemberId(Long memberId);

    List<CalendarExercise> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    CalendarExercise PostExerciseByDate(InsertRequest dto);

    CalendarExercise PutExerciseByDate(UpdateRequest dto);

    void DeleteExerciseByDate(Long id);
}
