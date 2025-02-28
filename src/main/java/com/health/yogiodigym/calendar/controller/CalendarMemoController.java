package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarMemoDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarMemoDto.SelectRequest;
import com.health.yogiodigym.calendar.dto.CalendarMemoDto.UpdateRequest;
import com.health.yogiodigym.calendar.service.impl.CalendarMemoServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
public class CalendarMemoController {

    private final CalendarMemoServiceImpl calendarMemoServiceimpl;

    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {

        List<SelectRequest> calendarMemo =  calendarMemoServiceimpl.findByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_CALENDAR_MEMO_SUCCESS.getMessage(), calendarMemo));
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<SelectRequest> calendarMemo =  calendarMemoServiceimpl.findByDateAndMemberId(requestedDate, memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, GET_ONE_CALENDAR_MEMO_SUCCESS.getMessage(), calendarMemo));
    }

    @PostMapping("/date/post")
    public ResponseEntity<?> postMemoByDate(@RequestBody InsertRequest dto) {

        calendarMemoServiceimpl.postMemoByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, POST_CALENDAR_MEMO_SUCCESS.getMessage(), null));
    }



    @PutMapping("/date/put")
    public ResponseEntity<?> putMemoByDate(@RequestBody UpdateRequest dto) {

        calendarMemoServiceimpl.putMemoByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, PUT_CALENDAR_MEMO_SUCCESS.getMessage(), null));
    }

    @DeleteMapping("/date/delete/{id}")
    public ResponseEntity<?> DeleteMemoByDate(@PathVariable("id") Long id) {

        calendarMemoServiceimpl.deleteMemoByDate(id);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, DELETE_CALENDAR_MEMO_SUCCESS.getMessage(), null));
    }

}
