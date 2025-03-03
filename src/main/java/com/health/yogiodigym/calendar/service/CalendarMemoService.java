package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarMemoDto.*;

import java.time.LocalDate;
import java.util.List;

public interface CalendarMemoService {

    List<CalendarMemoSelectDto> findByMemberId(Long memberId);

    List<CalendarMemoSelectDto> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    void postMemoByDate(CalendarMemoInsertDto dto);

    void putMemoByDate(CalendarMemoUpdateDto dto);

    void deleteMemoByDate(Long id);

}
