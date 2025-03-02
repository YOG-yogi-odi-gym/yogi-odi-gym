package com.health.yogiodigym.board.controller;

import com.health.yogiodigym.board.dto.BoardDto.*;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

import static com.health.yogiodigym.common.message.SuccessMessage.SEARCH_GYMS_SUCCESS;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/search")
    public ResponseEntity<?> searchBoard(@RequestParam(required = false) String boardKeyword,
                                         @RequestParam(required = false) String searchColumn,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<BoardDetailDto> boards = boardService.searchBoards(boardKeyword, searchColumn, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_GYMS_SUCCESS.getMessage(), boards));
    }

    @PostMapping("/register")
    public RedirectView registerBoard(@ModelAttribute BoardRequestDto dto,
                                      @AuthenticationPrincipal MemberOAuth2User loginUser) {
        Member loginMember = loginUser.getMember();
        boardService.registerBoard(dto, loginMember);

        return new RedirectView("/board");
    }

    @PostMapping("/edit")
    public RedirectView editBoard(@ModelAttribute BoardDetailDto dto) {
        boardService.editBoard(dto);
        return new RedirectView("/board/" + dto.getId());
    }
}
