package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarFoodDto.UpdateRequest;
import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.service.impl.CalendarFoodServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/food")
public class CalendarFoodController {

    @Autowired
    private CalendarFoodServiceImpl calendarFoodServiceimpl;


    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {
        List<CalendarFood> calendarFood = calendarFoodServiceimpl.findByMemberId(memberId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 조회에 성공하였습니다.", calendarFood));

    }
    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<CalendarFood> calendarFood = calendarFoodServiceimpl.findByDateAndMemberId(requestedDate, memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 및 멤버 조회에 성공하였습니다.", calendarFood));
    }


    @PostMapping("/date/post")
    public ResponseEntity<?> PostExerciseByDate(@RequestBody InsertRequest dto) {
        CalendarFood food = calendarFoodServiceimpl.PostFoodByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 삽입에 성공하였습니다.", food));
    }

    @PutMapping("/date/put")
    public ResponseEntity<?> PutExerciseByDate(@RequestBody UpdateRequest dto) {
        CalendarFood food = calendarFoodServiceimpl.PutFoodByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 수정에 성공하였습니다.", food));
    }

    @DeleteMapping("/date/delete/{id}")
    public void DeleteFoodByDate(@PathVariable("id") Long id) {

        calendarFoodServiceimpl.DeleteFoodByDate(id);
    }
}
