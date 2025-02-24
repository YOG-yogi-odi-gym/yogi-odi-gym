package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.UpdateRequest;
import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.InsertRequest;
import com.health.yogiodigym.calendar.entity.CalendarExercise;

import com.health.yogiodigym.calendar.service.CalendarExerciseService;
import com.health.yogiodigym.calendar.service.impl.CalendarExerciseServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exercise")
public class CalendarExerciseController {

    @Autowired
    private CalendarExerciseServiceImpl calendarExerciseService;


    @GetMapping
    public ResponseEntity<?> findByMemberId(@RequestParam("memberId") Long memberId) {
        List<CalendarExercise> calendarExercises = calendarExerciseService.findByMemberId(memberId);
        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 조회에 성공하였습니다.", calendarExercises));

    }

    @GetMapping("/date")
    public ResponseEntity<?> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<CalendarExercise> calendarExercises = calendarExerciseService.findByDateAndMemberId(requestedDate,memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 및 멤버 조회에 성공하였습니다.", calendarExercises));

    }


    @PostMapping("/date/post")
    public ResponseEntity<?> PostExerciseByDate(@RequestBody InsertRequest dto) {
        CalendarExercise exercise = calendarExerciseService.PostExerciseByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 삽입에 성공하였습니다.", exercise));
    }

    @PutMapping("/date/put")
    public ResponseEntity<?> PutExerciseByDate(@RequestBody UpdateRequest dto) {
        CalendarExercise exercise = calendarExerciseService.PutExerciseByDate(dto);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK, "회원 수정에 성공하였습니다.", exercise));
    }

    @DeleteMapping("/date/delete/{id}")
    public void DeleteExerciseByDate(@PathVariable("id") Long id) {

        calendarExerciseService.DeleteExerciseByDate(id);
    }
}
