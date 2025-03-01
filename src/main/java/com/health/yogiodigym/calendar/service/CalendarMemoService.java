package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.CalendarMemoDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarMemoDto.SelectRequest;
import com.health.yogiodigym.calendar.dto.CalendarMemoDto.UpdateRequest;

import java.time.LocalDate;
import java.util.List;

public interface CalendarMemoService {

    List<SelectRequest> findByMemberId(Long memberId);

    List<SelectRequest> findByDateAndMemberId(LocalDate selectedDate, Long memberId);

    void postMemoByDate(InsertRequest dto);

    void putMemoByDate(UpdateRequest dto);

    void deleteMemoByDate(Long id);

}
