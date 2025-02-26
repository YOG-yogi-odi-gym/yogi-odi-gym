package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.LessonDto;
import com.health.yogiodigym.calendar.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDto>> getLessonsByMember(
            @RequestParam("memberId") Long memberId) {

        List<LessonDto> lessons = lessonService.getLessonsByMemberId(memberId);
        return ResponseEntity.ok(lessons);
    }

    // 특정 회원이 신청한 강의 중, 특정 날짜에 해당하는 것만 조회
    @GetMapping("/date")
    public ResponseEntity<List<LessonDto>> getLessonsByMemberAndDate(
            @RequestParam("memberId") Long memberId,
            @RequestParam("date") String selectedDate) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);
        List<LessonDto> lessons = lessonService.getLessonsByMemberAndDate(memberId, requestedDate);
        return ResponseEntity.ok(lessons);
    }

}
