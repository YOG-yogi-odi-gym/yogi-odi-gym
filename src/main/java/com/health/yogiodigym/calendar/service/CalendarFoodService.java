package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.CalendarFood;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CalendarFoodService {

    List<CalendarFood> findByMemberId(Long memberId);

    List<CalendarFood> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    CalendarFood PostFoodByDate(String name, Float hundredGram, Float calories, LocalDate selectedDate, Long memberId);

    CalendarFood PutFoodByDate(Long id, String name, Float hundredGram, Float calories, LocalDate selectedDate, Long memberId) ;

    void DeleteFoodByDate(Long id);
}
