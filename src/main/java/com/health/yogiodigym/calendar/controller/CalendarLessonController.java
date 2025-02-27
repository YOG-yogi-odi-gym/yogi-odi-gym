package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.LessonDto;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.calendar.service.CalendarLessonService;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@NoArgsConstructor
public class CalendarLessonController {

    @Autowired
    private CalendarLessonService calendarLessonService;


    @GetMapping("/lesson")
    public ResponseEntity<List<LessonDto>> getLessonsByMember(
            @RequestParam("memberId") Long memberId) {

        List<LessonDto> lessons = calendarLessonService.getLessonsByMemberId(memberId);
        return ResponseEntity.ok(lessons);
    }

    // 특정 회원이 신청한 강의 중, 특정 날짜에 해당하는 것만 조회
    @GetMapping("/lesson/date")
    public ResponseEntity<List<LessonDto>> getLessonsByMemberAndDate(
            @RequestParam("memberId") Long memberId,
            @RequestParam("date") String selectedDate) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);
        List<LessonDto> lessons = calendarLessonService.getLessonsByMemberAndDate(memberId, requestedDate);
        return ResponseEntity.ok(lessons);
    }


    @GetMapping("/api/enrollments/{memberId}")
    //public List<LessonEnrollment> getEnrollments(@PathVariable Long memberId) {
        public List<LessonDto> getEnrollments(@PathVariable Long memberId) {
        return calendarLessonService.getLessonsByMemberId(memberId);
    }

}
