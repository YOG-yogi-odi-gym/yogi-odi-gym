package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import com.health.yogiodigym.calendar.service.CallendarMemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/memo")
public class CalendarMemoController {

    @Autowired
    private CallendarMemoService calendarMemoService;

    @GetMapping
    public List<CalendarMemo> findMemoById(@RequestParam("memberId") Long memberId) {
        return calendarMemoService.findMemoById(memberId);
    }

    @GetMapping("/date")
    public List<CalendarMemo> findMemoByDate(@RequestParam("date") String selectedDate, @RequestParam("memberId") Long memberId) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        return calendarMemoService.findMemoByDate(requestedDate,memberId);
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
    public CalendarMemo putMemoByDate(@RequestParam("id") Long id,
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
