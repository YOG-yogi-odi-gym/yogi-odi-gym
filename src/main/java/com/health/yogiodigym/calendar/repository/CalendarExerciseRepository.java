package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.CalendarExercise;
import com.health.yogiodigym.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface CalendarExerciseRepository extends JpaRepository<CalendarExercise, Long> {

    List<CalendarExercise> findByDateAndMemberId(LocalDate date, Long memberId);

    List<CalendarExercise> findByMemberId(Long memberId);

    @Query("SELECT SUM(ce.time) FROM CalendarExercise ce WHERE ce.member = :member AND ce.date BETWEEN :startDate AND :endDate GROUP BY ce.date")
    List<Float> findAvgExerciseTimeByDate(Member member, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(ce.calories) FROM CalendarExercise ce WHERE ce.member = :member AND ce.date BETWEEN :startDate AND :endDate GROUP BY ce.date")
    List<Float> findAvgCalorieByDate(Member member, LocalDate startDate, LocalDate endDate);
}