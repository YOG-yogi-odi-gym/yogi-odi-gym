package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarLessonDto;
import com.health.yogiodigym.calendar.service.impl.CalendarLessonServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/calendar/lesson")
@RequiredArgsConstructor
public class CalendarLessonController {

    private final CalendarLessonServiceImpl calendarLessonServiceimpl;

    @GetMapping
    public ResponseEntity<?> getLessonsByMember(@RequestParam("memberId") Long memberId) {

        List<CalendarLessonDto> calendarLesson = calendarLessonServiceimpl.getLessonsByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_CALENDAR_LESSON_SUCCESS.getMessage(), calendarLesson));
    }

    // 특정 회원이 신청한 강의 중, 특정 날짜에 해당하는 것만 조회
    @GetMapping("/date/get")
    public ResponseEntity<?> getLessonsByMemberAndDate(
            @RequestParam("memberId") Long memberId,
            @RequestParam("date") String selectedDate) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<CalendarLessonDto> calendarLesson = calendarLessonServiceimpl.getLessonsByMemberAndDate(memberId,requestedDate);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_ONE_CALENDAR_LESSON_SUCCESS.getMessage(), calendarLesson));
    }

}
