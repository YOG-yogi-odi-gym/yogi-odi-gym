package com.health.yogiodigym.board.repository;

import com.health.yogiodigym.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b " +
            "JOIN b.member m " +
            "LEFT JOIN b.category c " +
            "WHERE (:keyword IS NULL OR (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.context) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')))) " +
            "AND (:column = 'title' AND LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "OR (:column = 'name' AND LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "OR (:column = 'context' AND LOWER(b.context) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:categories IS NULL OR c.id IN :categories) ")
    Page<Board> searchBoards(@Param("keyword") String keyword,
                             @Param("column") String column,
                             @Param("categories") List<Long> categories,
                             Pageable pageable);

    @Query("SELECT b FROM Board b " +
            "JOIN b.member m " +
            "LEFT JOIN b.category c " +
            "WHERE b.member.id = :id " +
            "AND (:keyword IS NULL OR (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.context) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')))) " +
            "AND (:column = 'title' AND LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "OR (:column = 'name' AND LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "OR (:column = 'context' AND LOWER(b.context) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:categories IS NULL OR c.id IN :categories) ")
    Page<Board> searchMyBoards(@Param("id") Long id,
                             @Param("keyword") String keyword,
                             @Param("column") String column,
                             @Param("categories") List<Long> categories,
                             Pageable pageable);

    @Query("SELECT b FROM Board b " +
            "LEFT JOIN b.category c " +
            "WHERE c.id IN :categories")
    Page<Board> findByCategories(@Param("categories") List<Long> categories, Pageable pageable);

    @Modifying
    @Query("UPDATE Board b SET b.view = b.view + 1 WHERE b.id = :id")
    void incrementViewCount(@Param("id") Long id);

    @Query("SELECT b FROM Board b WHERE b.member.id = :id")
    Page<Board> findByMemberId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.member.id = :id AND b.category.id IN :categories")
    Page<Board> findByMemberIdAndCategories(@Param("id") Long id, @Param("categories") List<Long> categories, Pageable pageable);

    List<Board> findAllByOrderByIdDesc();

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:boardKeyword% OR b.member.name LIKE %:boardKeyword% ORDER BY b.createDateTime DESC")
    List<Board> adminSearchBoards(@Param("boardKeyword") String boardKeyword);

}
