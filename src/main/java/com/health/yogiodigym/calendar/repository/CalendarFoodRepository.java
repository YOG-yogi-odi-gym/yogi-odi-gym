package com.health.yogiodigym.calendar.repository;


import com.health.yogiodigym.calendar.entity.CalendarFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarFoodRepository extends JpaRepository<CalendarFood, Long> {

    List<CalendarFood> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    List<CalendarFood> findByMemberId(Long memberId);


}
