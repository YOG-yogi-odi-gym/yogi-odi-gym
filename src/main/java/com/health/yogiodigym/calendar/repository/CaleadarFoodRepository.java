package com.health.yogiodigym.calendar.repository;


import com.health.yogiodigym.calendar.entity.CalendarFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CaleadarFoodRepository extends JpaRepository<CalendarFood, Long> {

    @Query("SELECT cf FROM CalendarFood cf WHERE cf.date = :selectedDate AND cf.member.id = :memberId")
    List<CalendarFood> findFoodByDate(@Param("selectedDate") LocalDate selectedDate, @Param("memberId") Long memberId);

    @Query("SELECT cf FROM CalendarFood cf WHERE cf.member.id = :memberId")
    List<CalendarFood> findFoodById(@Param("memberId") Long memberId);

}
