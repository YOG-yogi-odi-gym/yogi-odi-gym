package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.LessonEnrollment;
import com.health.yogiodigym.calendar.service.LessonEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class LessonEnrollmentController {

    @Autowired
    private LessonEnrollmentService lessonEnrollmentService;

    @GetMapping("/{memberId}")
    public List<LessonEnrollment> getEnrollments(@PathVariable Long memberId) {
        return lessonEnrollmentService.getLessonsByMemberId(memberId);
    }
}