package com.health.yogiodigym.lesson.service.impl;

import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import com.health.yogiodigym.lesson.service.LessonEnrollmentService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonEnrollmentServiceImpl implements LessonEnrollmentService {

    private final LessonEnrollmentRepository lessonEnrollmentRepository;
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean enrollLesson(Long memberId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (lessonEnrollmentRepository.existsByLessonAndMember(lesson, member)) {
            throw new IllegalStateException("이미 수강 중인 강의입니다.");
        }

        if (lesson.getCurrent() >= lesson.getMax()) {
            return false;
        }

        lessonEnrollmentRepository.save(LessonEnrollment.builder()
                .lesson(lesson)
                .member(member)
                .build());

        lesson.incrementCurrent();
        lessonRepository.save(lesson);
        return true;
    }

    @Override
    public boolean cancelEnrollment(Long memberId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        LessonEnrollment enrollment = lessonEnrollmentRepository.findByLessonAndMember(lesson, member)
                .orElseThrow(() -> new IllegalStateException("수강 내역이 없습니다."));

        lessonEnrollmentRepository.delete(enrollment);
        lesson.decrementCurrent();
        lessonRepository.save(lesson);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserEnrolled(Long memberId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return lessonEnrollmentRepository.existsByLessonAndMember(lesson, member);
    }
}