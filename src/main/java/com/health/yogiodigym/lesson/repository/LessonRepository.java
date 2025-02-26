package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l WHERE l.title LIKE %:lessonKeyword% OR l.master.name LIKE %:lessonKeyword%")
    List<Lesson> searchLessons(@Param("lessonKeyword") String lessonKeyword);

}
