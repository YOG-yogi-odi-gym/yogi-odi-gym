package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.LessonDto;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import com.health.yogiodigym.calendar.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonEnrollmentRepository lessonEnrollmentRepository;

    private final LessonRepository lessonRepository;

    public Lesson selectLesson(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다"));
    }

    private int getDayBitmask(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> 1;    // 0b0000001
            case TUESDAY -> 2;   // 0b0000010
            case WEDNESDAY -> 4; // 0b0000100
            case THURSDAY -> 8;  // 0b0001000
            case FRIDAY -> 16;   // 0b0010000
            case SATURDAY -> 32; // 0b0100000
            case SUNDAY -> 64;   // 0b1000000
        };
    }

    private boolean isMatchingDay(LocalDate date, int dayBitmask) {
        int dayValue = getDayBitmask(date.getDayOfWeek());
        return (dayBitmask & dayValue) != 0;
    }

    // 특정 회원이 신청한 강의 일정 조회
    public List<LessonDto> getLessonsByMemberId(Long memberId) {
        List<LessonEnrollment> enrollments = lessonEnrollmentRepository.findByMemberId(memberId);
        List<LessonDto> result = new ArrayList<>();

        for (LessonEnrollment enrollment : enrollments) {
            Lesson lesson = enrollment.getLesson();
            LocalDate current = lesson.getStartDay();
            int dayBitmask = lesson.getDays();

            while (!current.isAfter(lesson.getEndDay())) {
                if (isMatchingDay(current, dayBitmask)) {
                    result.add(new LessonDto(current, lesson));
                }
                current = current.plusDays(1);
            }
        }
        return result;
    }

    public List<LessonDto> getLessonsByMemberAndDate(Long memberId, LocalDate selectedDate) {
        List<LessonEnrollment> enrollments = lessonEnrollmentRepository.findByMemberId(memberId);
        List<LessonDto> result = new ArrayList<>();

        for (LessonEnrollment enrollment : enrollments) {
            Lesson lesson = enrollment.getLesson();
            int dayBitmask = lesson.getDays();

            if (!selectedDate.isBefore(lesson.getStartDay()) &&
                    !selectedDate.isAfter(lesson.getEndDay()) &&
                    isMatchingDay(selectedDate, dayBitmask)) {

                result.add(new LessonDto(selectedDate, lesson));
            }
        }
        return result;
    }
}