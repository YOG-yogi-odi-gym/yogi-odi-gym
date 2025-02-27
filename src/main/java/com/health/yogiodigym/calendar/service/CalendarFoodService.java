package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.*;

import java.time.LocalDate;
import java.util.List;

public interface CalendarFoodService {

    List<SelectRequest> findByMemberId(Long memberId);

    List<SelectRequest> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    void postFoodByDate(InsertRequest dto);

    void putFoodByDate(UpdateRequest dto) ;

    void deleteFoodByDate(Long id);
}
