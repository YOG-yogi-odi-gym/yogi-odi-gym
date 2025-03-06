package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.CalendarLessonDto;
import com.health.yogiodigym.calendar.service.CalendarLessonService;
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

    private final CalendarLessonService calendarLessonService;

    @GetMapping
    public ResponseEntity<?> getLessonsByMember(@RequestParam("memberId") Long memberId) {

        List<CalendarLessonDto> calendarLesson = calendarLessonService.getLessonsByMemberId(memberId);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_CALENDAR_LESSON_SUCCESS.getMessage(), calendarLesson));
    }

    @GetMapping("/date")
    public ResponseEntity<?> getLessonsByMemberAndDate(
            @RequestParam("memberId") Long memberId,
            @RequestParam("date") String selectedDate) {

        LocalDate requestedDate = LocalDate.parse(selectedDate);

        List<CalendarLessonDto> calendarLesson = calendarLessonService.getLessonsByMemberAndDate(memberId,requestedDate);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_ONE_CALENDAR_LESSON_SUCCESS.getMessage(), calendarLesson));
    }

}
