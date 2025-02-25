package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarFoodDto.UpdateRequest;
import com.health.yogiodigym.calendar.entity.CalendarFood;

import java.time.LocalDate;
import java.util.List;

public interface CalendarFoodService {

    List<CalendarFood> findByMemberId(Long memberId);

    List<CalendarFood> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    CalendarFood PostFoodByDate(InsertRequest dto);

    CalendarFood PutFoodByDate(UpdateRequest dto) ;

    void DeleteFoodByDate(Long id);
}
