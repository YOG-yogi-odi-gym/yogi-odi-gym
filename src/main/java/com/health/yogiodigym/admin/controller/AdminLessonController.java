package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.dto.LessonDto.*;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.ADMIN_LESSON_DELETE_SUCCESS;
import static com.health.yogiodigym.common.message.SuccessMessage.ADMIN_LESSON_SEARCH_SUCCESS;


@RestController
@RequestMapping("/api/admin/lesson")
@RequiredArgsConstructor
public class AdminLessonController {

    private final AdminLessonService adminLessonService;

    @GetMapping("/search")
    public ResponseEntity<?> adminSearchLessons(@RequestParam String lessonKeyword) {
        List<LessonResponseDto> lessons = adminLessonService.adminSearchLessons(lessonKeyword);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ADMIN_LESSON_SEARCH_SUCCESS.getMessage(), lessons));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> adminDeleteLesson(@RequestBody List<Long> ids) {

        adminLessonService.deleteAllById(ids);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK,ADMIN_LESSON_DELETE_SUCCESS.getMessage(), null));

    }
}
