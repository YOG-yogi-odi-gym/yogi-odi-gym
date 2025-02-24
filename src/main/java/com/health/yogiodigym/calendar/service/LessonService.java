package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.Lesson;
import com.health.yogiodigym.calendar.entity.LessonDTO;
import com.health.yogiodigym.calendar.entity.LessonEnrollment;
import com.health.yogiodigym.calendar.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.calendar.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonEnrollmentRepository lessonEnrollmentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public Lesson selectLesson(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다"));
    }

    // 요일을 비트마스크로 변환하는 메서드
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

    // 해당 날짜의 요일이 비트마스크에 포함되는지 확인
    private boolean isMatchingDay(LocalDate date, int dayBitmask) {
        int dayValue = getDayBitmask(date.getDayOfWeek());
        return (dayBitmask & dayValue) != 0;
    }

    // 특정 회원이 신청한 강의 일정 조회
    public List<LessonDTO> getLessonsByMemberId(Long memberId) {
        List<LessonEnrollment> enrollments = lessonEnrollmentRepository.findByMemberId(memberId);
        List<LessonDTO> result = new ArrayList<>();

        for (LessonEnrollment enrollment : enrollments) {
            Lesson lesson = enrollment.getLesson();
            LocalDate current = lesson.getStartDay();
            int dayBitmask = lesson.getDay();

            while (!current.isAfter(lesson.getEndDay())) {
                if (isMatchingDay(current, dayBitmask)) {
                    result.add(new LessonDTO(current, lesson));
                }
                current = current.plusDays(1);
            }
        }
        return result;
    }

    public List<LessonDTO> getLessonsByMemberAndDate(Long memberId, LocalDate selectedDate) {
        List<LessonEnrollment> enrollments = lessonEnrollmentRepository.findByMemberId(memberId);
        List<LessonDTO> result = new ArrayList<>();

        for (LessonEnrollment enrollment : enrollments) {
            Lesson lesson = enrollment.getLesson();
            int dayBitmask = lesson.getDay();

            if (!selectedDate.isBefore(lesson.getStartDay()) &&
                    !selectedDate.isAfter(lesson.getEndDay()) &&
                    isMatchingDay(selectedDate, dayBitmask)) {

                result.add(new LessonDTO(selectedDate, lesson));
            }
        }
        return result;
    }
}