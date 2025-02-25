package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.service.CalendarFoodService;
import com.health.yogiodigym.calendar.service.impl.CalendarFoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/food")
public class CalendarFoodController {

    @Autowired
    private CalendarFoodServiceImpl calendarFoodService;

    @GetMapping
    public List<CalendarFood> findByMemberId(@RequestParam("memberId") Long memberId) {
        return calendarFoodService.findByMemberId(memberId);
    }

    @GetMapping("/date")
    public List<CalendarFood> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarFoodService.findByDateAndMemberId(requestedDate,memberId);
    }

    @PostMapping("/date/post")
    public CalendarFood PostFoodByDate(@RequestParam("name") String name,
                                       @RequestParam("hundredGram") Float hundredGram,
                                       @RequestParam("calories") Float calories,
                                       @RequestParam("date") String selectedDate,
                                       @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarFoodService.PostFoodByDate(name,hundredGram,calories,requestedDate,memberId);
    }

    @PutMapping("/date/put")
    public CalendarFood putFoodByDate(@RequestParam("id") Long id,
                                      @RequestParam("name") String name,
                                      @RequestParam("hundredGram") Float hundredGram,
                                      @RequestParam("calories") Float calories,
                                      @RequestParam("date") String selectedDate,
                                      @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarFoodService.PutFoodByDate(id,name,hundredGram,calories,requestedDate,memberId);
    }

    @DeleteMapping("/date/delete")
    public void DeleteFoodByDate(@RequestParam("id") Long id) {

        calendarFoodService.DeleteFoodByDate(id);
    }
}
