package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.service.service.AdminLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminLessonController {

    private final AdminLessonService adminLessonService;
}
