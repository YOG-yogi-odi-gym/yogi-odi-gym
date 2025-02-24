package com.health.yogiodigym.calendar.repository;


import com.health.yogiodigym.calendar.entity.CalendarFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarFoodRepository extends JpaRepository<CalendarFood, Long> {

    List<CalendarFood> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    List<CalendarFood> findByMemberId(Long memberId);


}
