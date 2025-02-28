package com.health.yogiodigym.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendar")
public class CalendarViewController {


    @GetMapping("/select_food")
    public String showFoodSelection() {

        return "calendar/select_food";

    }

    @GetMapping("/select_exercise")
    public String showExerciseSelection() {

        return "calendar/select_exercise";

    }

    @PostMapping("/cal")
    public String memberTestPage() {
        return "calendar/cal";
    }

    @GetMapping("/cal")
    public String memberTestPageGet() {
        return "calendar/cal";
    }
}
