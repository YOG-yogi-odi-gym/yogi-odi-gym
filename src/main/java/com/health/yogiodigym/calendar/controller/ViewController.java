package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {


    @Autowired
    private LessonService lessonService;


    @GetMapping("/")
    public String showMemberList() {

        return "login";
    }

    @GetMapping("/detail/{id}")
    public String showLessonStatus(Model model, @PathVariable("id") Long lessonId) {

        model.addAttribute("id", lessonService.selectLesson(lessonId));
        return "lesson";
    }

    @GetMapping("/select_food")
    public String showFoodSelection() {

        return "select_food";

    }

    @GetMapping("/select_exercise")
    public String showExerciseSelection() {

        return "select_exercise";

    }


}