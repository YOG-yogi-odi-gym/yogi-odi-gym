package com.health.yogiodigym.board.service;

import com.health.yogiodigym.board.dto.BoardDto.*;
import com.health.yogiodigym.board.entity.Board;
import com.health.yogiodigym.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    Page<BoardDetailDto> searchBoards(String keyword, String column, List<Long> categories, Pageable pageable);

    void registerBoard(BoardRequestDto dto, Member member);

    BoardDetailDto findBoardById(Long id);

    BoardDetailDto getBoardDetail(Long id);

    void editBoard(BoardDetailDto dto);
}
