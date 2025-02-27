package com.health.yogiodigym.lesson.service;

import com.health.yogiodigym.lesson.entity.LessonEnrollment;

import java.util.List;

public interface LessonEnrollmentService {

    boolean enrollLesson(Long memberId, Long lessonId);

    boolean cancelEnrollment(Long memberId, Long lessonId);

    boolean isUserEnrolled(Long memberId, Long lessonId);

    //임시추가
    public List<LessonEnrollment> getLessonsByMemberId(Long memberId);
}