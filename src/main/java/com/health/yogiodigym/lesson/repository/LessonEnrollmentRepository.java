package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonEnrollmentRepository extends JpaRepository<LessonEnrollment, Long> {
    List<LessonEnrollment> findAllByMember(Member member);
}
