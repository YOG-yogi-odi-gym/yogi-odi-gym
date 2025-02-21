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
import java.util.Arrays;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonEnrollmentRepository lessonEnrollmentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public Lesson selectLesson(Long lessonId) {
        return lessonRepository.getById(lessonId);
    }

    // 특정 날짜가 강의 요일 리스트(dayList) 중 하나와 매칭되는지 확인
    private boolean isMatchingDay(LocalDate date, List<String> dayList) {
        String koreanDay = getKoreanDayOfWeek(date.getDayOfWeek());
        return dayList.contains(koreanDay);  // 예: ["월", "수"] 리스트에 포함 여부 확인
    }

    // 영어 요일을 한글 요일로 변환
    private String getKoreanDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "월";
            case TUESDAY -> "화";
            case WEDNESDAY -> "수";
            case THURSDAY -> "목";
            case FRIDAY -> "금";
            case SATURDAY -> "토";
            case SUNDAY -> "일";
        };
    }

    // 특정 회원이 신청한 강의 일정 조회
    public List<LessonDTO> getLessonsByMemberId(Long memberId) {
        List<LessonEnrollment> enrollments = lessonEnrollmentRepository.findEnrollByMemberId(memberId);
        List<LessonDTO> result = new ArrayList<>();

        for (LessonEnrollment enrollment : enrollments) {
            Lesson lesson = enrollment.getLesson();
            LocalDate current = lesson.getStartDay();

            while (!current.isAfter(lesson.getEndDay())) {
                if (isMatchingDay(current, Arrays.asList(lesson.getDay().split(",")))) {
                    result.add(new LessonDTO(current, lesson));
                }
                current = current.plusDays(1);
            }
        }

        return result;
    }

    public List<LessonDTO> getLessonsByMemberAndDate(Long memberId, LocalDate selectedDate) {
        List<LessonEnrollment> enrollments = lessonEnrollmentRepository.findEnrollByMemberId(memberId);
        List<LessonDTO> result = new ArrayList<>();

        for (LessonEnrollment enrollment : enrollments) {
            Lesson lesson = enrollment.getLesson();

            if (!selectedDate.isBefore(lesson.getStartDay()) &&
                    !selectedDate.isAfter(lesson.getEndDay()) &&
                    isMatchingDay(selectedDate, Arrays.asList(lesson.getDay().split(",")))) {

                result.add(new LessonDTO(selectedDate, lesson));
            }
        }
        return result;
    }
}
