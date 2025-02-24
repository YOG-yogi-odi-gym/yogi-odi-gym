package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto;
import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.UpdateRequest;
import com.health.yogiodigym.calendar.entity.CalendarExercise;
import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarExerciseRepository;
import com.health.yogiodigym.calendar.repository.DataExerciseRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import com.health.yogiodigym.common.exception.DataExerciseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface CalendarExerciseService {

    List<CalendarExercise> findByMemberId(Long memberId);

    List<CalendarExercise> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    CalendarExercise PostExerciseByDate(InsertRequest dto);

    CalendarExercise PutExerciseByDate(UpdateRequest dto);

    void DeleteExerciseByDate(Long id);
}
