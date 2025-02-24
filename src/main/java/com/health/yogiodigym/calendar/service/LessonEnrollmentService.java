package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.LessonEnrollment;
import com.health.yogiodigym.calendar.repository.LessonEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonEnrollmentService {

    @Autowired
    private LessonEnrollmentRepository lessonEnrollmentRepository;

    public List<LessonEnrollment> getLessonsByMemberId(Long memberId) {
        return lessonEnrollmentRepository.findByMemberId(memberId);
    }


}
