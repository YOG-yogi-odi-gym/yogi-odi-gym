package com.health.yogiodigym.board.repository;

import com.health.yogiodigym.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByIdDesc();

    @Query("SELECT b FROM Board b WHERE b.title LIKE %:boardKeyword% OR b.member.name LIKE %:boardKeyword% ORDER BY b.createDateTime DESC")
    List<Board> adminSearchBoards(@Param("boardKeyword") String boardKeyword);
}
