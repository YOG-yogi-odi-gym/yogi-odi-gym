package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.dto.LessonDto.*;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.LESSON_SEARCH_SUCCESS;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminLessonController {

    private final AdminLessonService adminLessonService;

    @GetMapping("/lesson/search")
    public ResponseEntity<?> searchLessons(@RequestParam String lessonKeyword) {
        List<LessonResponseDto> lessons = adminLessonService.searchLessons(lessonKeyword);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, LESSON_SEARCH_SUCCESS.getMessage(), lessons));
    }
}
