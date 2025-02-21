package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.LessonDTO;
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

    // 입력값 없이 Lesson 정보에서 날짜 및 요일을 자동으로 처리하여 JSON 응답 반환
    // 특정 회원이 신청한 모든 강의 일정 가져오기
    // 특정 회원이 신청한 모든 강의 일정 조회 (시작~종료 날짜 포함)
    @GetMapping
    public ResponseEntity<List<LessonDTO>> getLessonsByMember(
            @RequestParam("memberId") Long memberId) {

        List<LessonDTO> lessons = lessonService.getLessonsByMemberId(memberId);
        return ResponseEntity.ok(lessons);
    }

    // 특정 회원이 신청한 강의 중, 특정 날짜에 해당하는 것만 조회
    @GetMapping("/date")
    public ResponseEntity<List<LessonDTO>> getLessonsByMemberAndDate(
            @RequestParam("memberId") Long memberId,
            @RequestParam("date") String selectedDate) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);
        List<LessonDTO> lessons = lessonService.getLessonsByMemberAndDate(memberId, requestedDate);
        return ResponseEntity.ok(lessons);
    }

}
