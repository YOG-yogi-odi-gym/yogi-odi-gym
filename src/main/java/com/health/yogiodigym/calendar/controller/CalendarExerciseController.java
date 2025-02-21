package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.CalendarExercise;
import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.entity.CalendarMemo;
import com.health.yogiodigym.calendar.service.CalendarExcerciseService;
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
    private CalendarExcerciseService calendarExerciseService;

    @GetMapping
    public List<CalendarExercise> findExerciseById(@RequestParam("memberId") Long memberId) {
        return calendarExerciseService.findExerciseById(memberId);
    }

    @GetMapping("/date")
    public List<CalendarExercise> findExerciseByName(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarExerciseService.findExerciseByDate(requestedDate,memberId);
    }

    @PostMapping("/date/post")
    public CalendarExercise PostExerciseByDate(@RequestParam("name") String name,
                                       @RequestParam("time") Float time,
                                       @RequestParam("calories") Float calories,
                                       @RequestParam("date") String selectedDate,
                                       @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarExerciseService.PostExerciseByDate(name,time,calories,requestedDate,memberId);
    }

    @PutMapping("/date/put")
    public CalendarExercise PutFoodByDate(@RequestParam("id") Long id,
                                      @RequestParam("name") String name,
                                      @RequestParam("time") Float time,
                                      @RequestParam("calories") Float calories,
                                      @RequestParam("date") String selectedDate,
                                      @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarExerciseService.PutExerciseByDate(id,name,time,calories,requestedDate,memberId);
    }

    @DeleteMapping("/date/delete")
    public void DeleteFoodByDate(@RequestParam("id") Long id) {

        calendarExerciseService.DeleteExerciseByDate(id);
    }
}
