package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {



}
