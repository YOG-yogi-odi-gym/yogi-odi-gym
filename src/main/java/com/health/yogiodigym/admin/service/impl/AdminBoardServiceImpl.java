package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.BoardDto.*;
import com.health.yogiodigym.admin.service.service.AdminBoardService;
import com.health.yogiodigym.board.entity.Board;
import com.health.yogiodigym.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllBoards() {
        return boardRepository.getAllBoards();
    }

    public List<BoardResponseDto> adminSearchBoards(String boardKeyword) {

        List<Board> boards = boardRepository.adminSearchBoards(boardKeyword);

        List<BoardResponseDto> boardDtos = new ArrayList<>();
        for (Board board : boards) {
            boardDtos.add(BoardResponseDto.from(board));
        }
        return boardDtos;
    }


}
