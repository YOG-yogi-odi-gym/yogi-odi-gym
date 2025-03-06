package com.health.yogiodigym.calendar.controller;


import com.health.yogiodigym.calendar.dto.CalendarFoodDto.*;
import com.health.yogiodigym.calendar.service.CalendarFoodService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/calendar/food")
@RequiredArgsConstructor
public class CalendarFoodController {

    private final CalendarFoodService calendarFoodService;

    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {

        List<CalendarFoodSelectDto> calendarFood = calendarFoodService.findByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_CALENDAR_FOOD_SUCCESS.getMessage(), calendarFood));
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<CalendarFoodSelectDto> calendarFood = calendarFoodService.findByDateAndMemberId(requestedDate, memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, GET_ONE_CALENDAR_FOOD_SUCCESS.getMessage(), calendarFood));
    }

    @PostMapping("/date")
    public ResponseEntity<?> postExerciseByDate(@RequestBody CalendarFoodInsertDto dto) {

        calendarFoodService.postFoodByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, POST_CALENDAR_FOOD_SUCCESS.getMessage(), null));
    }

    @PutMapping("/date")
    public ResponseEntity<?> putExerciseByDate(@RequestBody CalendarFoodUpdateDto dto) {

        calendarFoodService.putFoodByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, PUT_CALENDAR_FOOD_SUCCESS.getMessage(), null));
    }

    @DeleteMapping("/date/{id}")
    public ResponseEntity<?>  deleteFoodByDate(@PathVariable("id") Long id) {
        calendarFoodService.deleteFoodByDate(id);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, DELETE_CALENDAR_FOOD_SUCCESS.getMessage(),null));
    }
}
