package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.LessonEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonEnrollmentRepository extends JpaRepository<LessonEnrollment, Long> {

    List<LessonEnrollment> findByMemberId(Long memberId);

}