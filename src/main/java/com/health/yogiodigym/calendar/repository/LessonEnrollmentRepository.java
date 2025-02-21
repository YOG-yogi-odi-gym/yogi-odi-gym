package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.LessonEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonEnrollmentRepository extends JpaRepository<com.health.yogiodigym.calendar.entity.LessonEnrollment, Long> {

    @Query("SELECT le FROM LessonEnrollment le " +
            "JOIN FETCH le.lesson l " +
            "JOIN FETCH le.member m " +
            "WHERE le.member.id = :memberId")
    List<com.health.yogiodigym.calendar.entity.LessonEnrollment> findEnrollByMemberId(@Param("memberId") Long memberId);

}