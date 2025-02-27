package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.*;
import com.health.yogiodigym.calendar.service.impl.CalendarExerciseServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class CalendarExerciseController {

    private final CalendarExerciseServiceImpl calendarExerciseServiceimpl;

    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {

        List<SelectRequest> calendarExercises = calendarExerciseServiceimpl.findByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, GET_CALENDAR_EXERCISE_SUCCESS.getMessage(), calendarExercises));

    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<SelectRequest> calendarExercises = calendarExerciseServiceimpl.findByDateAndMemberId(requestedDate,memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, GET_ONE_CALENDAR_EXERCISE_SUCCESS.getMessage(), calendarExercises));

    }

    @PostMapping("/date/post")
    public ResponseEntity<?> postExerciseByDate(@RequestBody InsertRequest dto) {

        calendarExerciseServiceimpl.postExerciseByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, POST_CALENDAR_EXERCISE_SUCCESS.getMessage(), null));
    }

    @PutMapping("/date/put")
    public ResponseEntity<?> putExerciseByDate(@RequestBody UpdateRequest dto) {
        calendarExerciseServiceimpl.putExerciseByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, PUT_CALENDAR_EXERCISE_SUCCESS.getMessage(), null));
    }

    @DeleteMapping("/date/delete/{id}")
    public ResponseEntity<?> deleteExerciseByDate(@PathVariable("id") Long id) {
        calendarExerciseServiceimpl.deleteExerciseByDate(id);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, DELETE_CALENDAR_EXERCISE_SUCCESS.getMessage(),null));
    }
}
