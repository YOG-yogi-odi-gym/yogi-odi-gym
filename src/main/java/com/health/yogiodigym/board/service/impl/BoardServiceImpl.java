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
        boolean allCategories = isAllCategorySelected(categories);
        boolean noKeyword = isKeywordEmpty(keyword);

        Page<Board> boardPage = noKeyword ? findBoardsByCategory(allCategories, categories, pageable)
                : searchOrFallback(keyword, column, allCategories ? null : categories, pageable);

        return boardPage.map(BoardDetailDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardDetailDto> searchMyBoards(Long id, String boardKeyword, String searchColumn, List<Long> categories, Pageable pageable) {
        boolean allCategories = isAllCategorySelected(categories);
        boolean noKeyword = isKeywordEmpty(boardKeyword);

        Page<Board> boardPage = noKeyword ? findMyBoardsByCategory(id, allCategories, categories, pageable)
                : mySearchOrFallback(id, boardKeyword, searchColumn, allCategories ? null : categories, pageable);

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
        boardRepository.save(board);
    }

    private boolean isKeywordEmpty(String keyword) {
        return keyword == null || keyword.isEmpty();
    }

    private boolean isAllCategorySelected(List<Long> categories) {
        return categories == null || categories.isEmpty() || categories.contains(999L);
    }

    private Page<Board> findBoardsByCategory(boolean allCategories, List<Long> categories, Pageable pageable) {
        return allCategories ? boardRepository.findAll(pageable) : boardRepository.findByCategoryIdIn(categories, pageable);
    }

    private Page<Board> searchOrFallback(String keyword, String column, List<Long> categories, Pageable pageable) {
        Page<Board> boardPage = boardRepository.searchBoards(keyword, column, categories, pageable);
        return boardPage.isEmpty() ? findBoardsByCategory(categories == null, categories, pageable) : boardPage;
    }

    private Page<Board> findMyBoardsByCategory(Long id, boolean allCategories, List<Long> categories, Pageable pageable) {
        return allCategories ? boardRepository.findByMemberId(id, pageable)
                : boardRepository.findByMemberIdAndCategoryIdIn(id, categories, pageable);
    }

    private Page<Board> mySearchOrFallback(Long id, String keyword, String column, List<Long> categories, Pageable pageable) {
        Page<Board> boardPage = boardRepository.searchMyBoards(id, keyword, column, categories, pageable);
        return boardPage.isEmpty() ? findBoardsByCategory(categories == null, categories, pageable) : boardPage;
    }
}
