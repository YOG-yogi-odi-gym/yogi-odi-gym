package com.health.yogiodigym.calendar.service.impl;
import com.health.yogiodigym.calendar.dto.CalendarLessonDto;
import com.health.yogiodigym.calendar.dto.CalendarMemberDto;
import com.health.yogiodigym.calendar.service.CalendarLessonService;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarLessonServiceImpl implements CalendarLessonService {

    private final LessonEnrollmentRepository lessonEnrollmentRepository;

    private final MemberRepository memberRepository;


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

    @Override
    @Transactional(readOnly = true)
    public List<CalendarLessonDto> getLessonsByMemberId(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        return lessonEnrollmentRepository.findAllByMember(member).stream()
                .flatMap(enrollment -> {
                    Lesson lesson = enrollment.getLesson();
                    int dayBitmask = lesson.getDays();

                    return lesson.getStartDay().datesUntil(lesson.getEndDay().plusDays(1))
                            .filter(date -> isMatchingDay(date, dayBitmask))
                            .map(date -> new CalendarLessonDto(date, lesson));
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarLessonDto> getLessonsByMemberAndDate(Long memberId, LocalDate selectedDate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        return lessonEnrollmentRepository.findAllByMember(member).stream()
                .map(LessonEnrollment::getLesson)
                .filter(lesson ->
                        !selectedDate.isBefore(lesson.getStartDay()) &&
                                !selectedDate.isAfter(lesson.getEndDay()) &&
                                isMatchingDay(selectedDate, lesson.getDays())
                )
                .map(lesson -> new CalendarLessonDto(selectedDate, lesson))
                .collect(Collectors.toList());
    }

}