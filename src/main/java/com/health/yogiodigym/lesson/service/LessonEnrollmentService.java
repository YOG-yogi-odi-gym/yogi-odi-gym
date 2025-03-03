package com.health.yogiodigym.lesson.service;

public interface LessonEnrollmentService {

    boolean enrollLesson(Long memberId, Long lessonId);

    boolean cancelEnrollment(Long memberId, Long lessonId);

    boolean isUserEnrolled(Long memberId, Long lessonId);
}