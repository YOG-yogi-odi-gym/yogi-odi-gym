package com.health.yogiodigym.board.controller;

import com.health.yogiodigym.board.dto.BoardDto.*;
import com.health.yogiodigym.board.dto.MemberBoardDto;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.lesson.dto.MemberLatLonDto;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardViewController {

    private final LessonService lessonService;
    private final BoardService boardService;

    @GetMapping
    public String showBoard(Model model) {
        model.addAttribute("categories", lessonService.getCategoriesByCode("board"));
        return "board/board";
    }

    @GetMapping("/register")
    public String showBoardRegister(Model model,
                                    @AuthenticationPrincipal MemberOAuth2User loginUser) {
        Member loginMember = loginUser.getMember();

        model.addAttribute("member", new MemberBoardDto(loginMember));
        model.addAttribute("categories", lessonService.getCategoriesByCode("board"));
        return "board/register";
    }

    @GetMapping("/{id}")
    public String showBoardDetail(@PathVariable Long id, Model model,
                                  @AuthenticationPrincipal MemberOAuth2User loginUser) {
        Member loginMember = loginUser.getMember();

        model.addAttribute("member", new MemberBoardDto(loginMember));
        model.addAttribute("board", boardService.getBoardDetail(id));
        return "board/detail";
    }

    @GetMapping("/{boardId}/edit")
    public String showBoardUpdate(@PathVariable Long boardId, Model model) {
        BoardDetailDto boardDetailDto = boardService.findBoardById(boardId);

        model.addAttribute("categories", lessonService.getCategoriesByCode("board"));
        model.addAttribute("board", boardDetailDto);
        return "board/edit";
    }
}
