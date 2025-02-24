package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.CalendarExercise;

import com.health.yogiodigym.calendar.service.CalendarExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exercise")
public class CalendarExerciseController {

    @Autowired
    private CalendarExerciseService calendarExerciseService;

    @GetMapping
    public List<CalendarExercise> findByMemberId(@RequestParam("memberId") Long memberId) {
        return calendarExerciseService.findByMemberId(memberId);
    }

    @GetMapping("/date")
    public List<CalendarExercise> findExerciseByName(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarExerciseService.findByDateAndMemberId(requestedDate,memberId);
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
    public CalendarExercise PutExerciseByDate(@RequestParam("id") Long id,
                                      @RequestParam("name") String name,
                                      @RequestParam("time") Float time,
                                      @RequestParam("calories") Float calories,
                                      @RequestParam("date") String selectedDate,
                                      @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarExerciseService.PutExerciseByDate(id,name,time,calories,requestedDate,memberId);
    }

    @DeleteMapping("/date/delete")
    public void DeleteExerciseByDate(@RequestParam("id") Long id) {

        calendarExerciseService.DeleteExerciseByDate(id);
    }
}
