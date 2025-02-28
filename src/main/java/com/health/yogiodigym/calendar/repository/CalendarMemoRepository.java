package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarMemoRepository extends JpaRepository<CalendarMemo, Long> {

    List<CalendarMemo> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    List<CalendarMemo> findByMemberId(Long memberId);


}
