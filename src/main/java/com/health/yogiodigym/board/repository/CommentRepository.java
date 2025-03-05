package com.health.yogiodigym.board.repository;

import com.health.yogiodigym.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardIdOrderByCreateDateTimeAsc(Long boardId);

    Long countByBoardId(Long boardId);
}
