package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarFoodDto.SelectRequest;
import com.health.yogiodigym.calendar.dto.CalendarFoodDto.UpdateRequest;
import com.health.yogiodigym.calendar.service.impl.CalendarFoodServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class CalendarFoodController {

    private final CalendarFoodServiceImpl calendarFoodServiceimpl;

    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {

        List<SelectRequest> calendarFood = calendarFoodServiceimpl.findByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_CALENDAR_FOOD_SUCCESS.getMessage(), calendarFood));
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<SelectRequest> calendarFood = calendarFoodServiceimpl.findByDateAndMemberId(requestedDate, memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, GET_ONE_CALENDAR_FOOD_SUCCESS.getMessage(), calendarFood));
    }

    @PostMapping("/date/post")
    public ResponseEntity<?> postExerciseByDate(@RequestBody InsertRequest dto) {

        calendarFoodServiceimpl.postFoodByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, POST_CALENDAR_FOOD_SUCCESS.getMessage(), null));
    }

    @PutMapping("/date/put")
    public ResponseEntity<?> putExerciseByDate(@RequestBody UpdateRequest dto) {

        calendarFoodServiceimpl.putFoodByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, PUT_CALENDAR_FOOD_SUCCESS.getMessage(), null));
    }

    @DeleteMapping("/date/delete/{id}")
    public ResponseEntity<?>  deleteFoodByDate(@PathVariable("id") Long id) {
        calendarFoodServiceimpl.deleteFoodByDate(id);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, DELETE_CALENDAR_FOOD_SUCCESS.getMessage(),null));
    }
}
