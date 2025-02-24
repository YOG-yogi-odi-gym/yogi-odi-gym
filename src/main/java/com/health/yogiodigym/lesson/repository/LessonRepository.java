package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.lesson.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l JOIN FETCH l.master WHERE l.title LIKE %:title%")
    Page<Lesson> findByTitleContaining(@Param("title") String title, Pageable pageable);
}