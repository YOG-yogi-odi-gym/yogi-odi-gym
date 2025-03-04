package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByMaster(Member member);
  
    @Query("SELECT l FROM Lesson l WHERE l.title LIKE %:lessonKeyword% OR l.master.name LIKE %:lessonKeyword%")
    List<Lesson> adminSearchLessons(@Param("lessonKeyword") String lessonKeyword);

    @Query("""
                SELECT l FROM Lesson l JOIN FETCH l.master 
                WHERE (:keyword IS NULL OR (CASE WHEN :column = 'name' THEN l.title ELSE l.location END) LIKE %:keyword%)
                AND (:days IS NULL OR BITAND(l.days, :days) > 0)
            """)
    Page<Lesson> searchLessons(@Param("keyword") String keyword,
                               @Param("column") String column,
                               @Param("days") Integer days,
                               Pageable pageable);

    @Query(value = "SELECT * FROM lesson l WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "(:column = 'name' AND l.title LIKE CONCAT('%', :keyword, '%')) OR " +
            "(:column = 'location' AND l.location LIKE CONCAT('%', :keyword, '%'))) " +
            "AND (:days IS NULL OR (l.days & :days) > 0) " +
            "AND (:categories IS NULL OR l.category_id IN :categories) ",
            countQuery = "SELECT count(*) FROM lesson l WHERE " +
                    "(:keyword IS NULL OR :keyword = '' OR " +
                    "(:column = 'name' AND l.title LIKE CONCAT('%', :keyword, '%')) OR " +
                    "(:column = 'location' AND l.location LIKE CONCAT('%', :keyword, '%'))) " +
                    "AND (:days IS NULL OR (l.days & :days) > 0) " +
                    "AND (:categories IS NULL OR l.category_id IN :categories)",
            nativeQuery = true)
    Page<Lesson> searchLessonsByCategories(
            @Param("keyword") String keyword,
            @Param("column") String column,
            @Param("days") Integer days,
            @Param("categories") List<Long> categories,
            Pageable pageable);

    Optional<Lesson> findByChatRoom(ChatRoom chatRoom);
}
