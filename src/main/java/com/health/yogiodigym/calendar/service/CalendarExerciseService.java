package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.*;

import java.time.LocalDate;
import java.util.List;

public interface CalendarExerciseService {

    List<SelectRequest> findByMemberId(Long memberId);

    List<SelectRequest> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    void postExerciseByDate(InsertRequest dto);

    void putExerciseByDate(UpdateRequest dto);

    void deleteExerciseByDate(Long id);
}
