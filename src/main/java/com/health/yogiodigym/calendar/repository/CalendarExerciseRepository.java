package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.CalendarExercise;
import com.health.yogiodigym.calendar.entity.CalendarMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarExerciseRepository extends JpaRepository<CalendarExercise, Long> {

    @Query("SELECT cr FROM CalendarExercise cr WHERE cr.date = :selectedDate AND cr.member.id = :memberId")
    List<CalendarExercise> findExerciseByDate(@Param("selectedDate") LocalDate selectedDate, @Param("memberId") Long memberId);

    @Query("SELECT cr FROM CalendarExercise cr WHERE cr.member.id = :memberId")
    List<CalendarExercise> findExerciseById(@Param("memberId") Long memberId);

}