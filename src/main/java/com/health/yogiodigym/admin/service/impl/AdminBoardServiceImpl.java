package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.BoardDto.*;
import com.health.yogiodigym.admin.service.service.AdminBoardService;
import com.health.yogiodigym.board.entity.Board;
import com.health.yogiodigym.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAllByOrderByIdDesc() {
        List<Board> boards = boardRepository.findAllByOrderByIdDesc();

        return boards.stream().map(BoardResponseDto::from).collect(Collectors.toList());
    }

    public List<BoardResponseDto> adminSearchBoards(String boardKeyword) {

        List<Board> boards = boardRepository.adminSearchBoards(boardKeyword);

        List<BoardResponseDto> boardDtos = new ArrayList<>();
        for (Board board : boards) {
            boardDtos.add(BoardResponseDto.from(board));
        }
        return boardDtos;
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        boardRepository.deleteAllById(ids);
    }


}
