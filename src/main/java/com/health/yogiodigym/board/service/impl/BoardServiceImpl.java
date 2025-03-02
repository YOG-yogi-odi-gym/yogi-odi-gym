package com.health.yogiodigym.board.service.impl;

import com.health.yogiodigym.board.entity.Board;
import com.health.yogiodigym.board.dto.BoardDto.*;
import com.health.yogiodigym.board.repository.BoardRepository;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.common.exception.BoardNotFoundException;
import com.health.yogiodigym.common.exception.CategoryNotFoundException;
import com.health.yogiodigym.common.exception.LessonNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardDetailDto> searchBoards(String keyword, String column, List<Long> categories, Pageable pageable) {
        Page<Board> boardPage;

        if (categories == null || categories.isEmpty() || categories.contains(999L)) {
            if (keyword == null || keyword.isEmpty()) {
                boardPage = boardRepository.findAll(pageable);
            } else {
                boardPage = boardRepository.searchBoards(keyword, column, null, pageable);
                if (boardPage.isEmpty()) {
                    boardPage = boardRepository.findAll(pageable);
                }
            }
        } else {
            if (keyword == null || keyword.isEmpty()) {
                boardPage = boardRepository.findByCategories(categories, pageable);
            } else {
                boardPage = boardRepository.searchBoards(keyword, column, categories, pageable);
                if (boardPage.isEmpty()) {
                    boardPage = boardRepository.findByCategories(categories, pageable);
                }
            }
        }

        return boardPage.map(BoardDetailDto::new);
    }

    @Override
    public void registerBoard(BoardRequestDto dto, Member member) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        Board board = Board.builder()
                .member(member)
                .category(category)
                .title(dto.getTitle())
                .context(dto.getContext())
                .createDateTime(LocalDateTime.now())
                .view(0)
                .edit(false)
                .build();

        boardRepository.save(board);
    }

    @Override
    public BoardDetailDto findBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));

        return new BoardDetailDto(board);
    }

    @Override
    public BoardDetailDto getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));

        board.incrementView();
        boardRepository.save(board);

        return new BoardDetailDto(board);
    }

    @Override
    public void editBoard(BoardDetailDto dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new BoardNotFoundException(dto.getId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));

        board.updateBoard(dto, category);
    }
}
