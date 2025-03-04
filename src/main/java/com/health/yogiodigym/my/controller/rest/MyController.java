package com.health.yogiodigym.my.controller.rest;

import com.health.yogiodigym.board.dto.BoardDto.*;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.LessonDto.*;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    private final BoardService boardService;
    private final LessonService lessonService;

    @GetMapping("/board")
    public ResponseEntity<?> searchMyBoard(@RequestParam(required = false) String boardKeyword,
                                           @RequestParam(required = false) String searchColumn,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @AuthenticationPrincipal MemberOAuth2User loginUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<BoardDetailDto> boards = boardService.searchMyBoards(loginUser.getMember().getId(), boardKeyword, searchColumn, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_BOARD_SUCCESS.getMessage(), boards));
    }

    @GetMapping("/lesson")
    public ResponseEntity<?> searchLessons(@RequestParam(required = false) String lessonKeyword,
                                           @RequestParam(required = false) String searchColumn,
                                           @RequestParam(required = false) Integer days,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @AuthenticationPrincipal MemberOAuth2User loginUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id"));
        Page<LessonSearchDto> lessons = lessonService.searchMyLessons(loginUser.getMember().getId(), lessonKeyword, searchColumn, days, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_LESSON_SUCCESS.getMessage(), lessons));
    }
}
