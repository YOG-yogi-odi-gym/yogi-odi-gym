package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import com.health.yogiodigym.calendar.service.CalendarMemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/memo")
public class CalendarMemoController {

    @Autowired
    private CalendarMemoService calendarMemoService;

    @GetMapping
    public List<CalendarMemo> findByMemberId(@RequestParam("memberId") Long memberId) {
        return calendarMemoService.findByMemberId(memberId);
    }

    @GetMapping("/date")
    public List<CalendarMemo> findByDateAndMemberId(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarMemoService.findByDateAndMemberId(requestedDate,memberId);
    }

    @PostMapping("/date/post")
    public CalendarMemo PostMemoByDate(@RequestParam("title") String title,
                                       @RequestParam("context") String context,
                                       @RequestParam("date") String selectedDate,
                                       @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarMemoService.PostMemoByDate(title,context,requestedDate,memberId);
    }



    @PutMapping("/date/put")
    public CalendarMemo PutMemoByDate(@RequestParam("id") Long id,
                                      @RequestParam("title") String title,
                                      @RequestParam("context") String context,
                                      @RequestParam("date") String selectedDate,
                                      @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarMemoService.PutMemoByDate(id, title, context, requestedDate, memberId);
    }

    @DeleteMapping("/date/delete")
    public void DeleteMemoByDate(@RequestParam("id") Long id) {

        calendarMemoService.DeleteMemoByDate(id);
    }

}
