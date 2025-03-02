package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.BoardDto.*;
import com.health.yogiodigym.admin.service.service.AdminBoardService;
import com.health.yogiodigym.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return boardRepository.findAllByOrderByIdDesc()
                .stream()
                .map(BoardResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDto> adminSearchBoards(String boardKeyword) {

        return boardRepository.adminSearchBoards(boardKeyword)
                .stream()
                .map(BoardResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        boardRepository.deleteAllById(ids);
    }


}
