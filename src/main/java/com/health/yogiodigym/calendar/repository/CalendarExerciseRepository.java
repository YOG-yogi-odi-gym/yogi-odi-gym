package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.CalendarExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarExerciseRepository extends JpaRepository<CalendarExercise, Long> {

    List<CalendarExercise> findByDateAndMemberId(LocalDate date, Long memberId);

    List<CalendarExercise> findByMemberId(Long memberId);

}