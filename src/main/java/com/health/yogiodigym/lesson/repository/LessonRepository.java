package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByMaster(Member member);
}
