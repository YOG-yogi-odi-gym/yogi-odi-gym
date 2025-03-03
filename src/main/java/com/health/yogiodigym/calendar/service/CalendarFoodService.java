package com.health.yogiodigym.calendar.service;


import com.health.yogiodigym.calendar.dto.CalendarFoodDto.*;


import java.time.LocalDate;
import java.util.List;

public interface CalendarFoodService {

    List<CalendarFoodSelectDto> findByMemberId(Long memberId);

    List<CalendarFoodSelectDto> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    void postFoodByDate(CalendarFoodInsertDto dto);

    void putFoodByDate(CalendarFoodUpdateDto dto) ;

    void deleteFoodByDate(Long id);
}
