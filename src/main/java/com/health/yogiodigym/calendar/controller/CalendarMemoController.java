package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarMemoDto.*;
import com.health.yogiodigym.calendar.service.CalendarMemoService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/calendar/memo")
@RequiredArgsConstructor
public class CalendarMemoController {

    private final CalendarMemoService calendarMemoService;

    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {

        List<CalendarMemoSelectDto> calendarMemo =  calendarMemoService.findByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_CALENDAR_MEMO_SUCCESS.getMessage(), calendarMemo));
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<CalendarMemoSelectDto> calendarMemo =  calendarMemoService.findByDateAndMemberId(requestedDate, memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, GET_ONE_CALENDAR_MEMO_SUCCESS.getMessage(), calendarMemo));
    }

    @PostMapping("/date")
    public ResponseEntity<?> postMemoByDate(@RequestBody CalendarMemoInsertDto dto) {

        calendarMemoService.postMemoByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, POST_CALENDAR_MEMO_SUCCESS.getMessage(), null));
    }



    @PutMapping("/date")
    public ResponseEntity<?> putMemoByDate(@RequestBody CalendarMemoUpdateDto dto) {

        calendarMemoService.putMemoByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, PUT_CALENDAR_MEMO_SUCCESS.getMessage(), null));
    }

    @DeleteMapping("/date/{id}")
    public ResponseEntity<?> deleteMemoByDate(@PathVariable("id") Long id) {

        calendarMemoService.deleteMemoByDate(id);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, DELETE_CALENDAR_MEMO_SUCCESS.getMessage(), null));
    }

}
