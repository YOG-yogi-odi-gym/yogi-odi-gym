package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.dto.BoardDto.*;
import com.health.yogiodigym.admin.dto.LessonDto.*;
import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.service.AdminBoardService;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.admin.service.service.AdminMemberService;
import com.health.yogiodigym.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminViewController {

    private final AdminMemberService adminMemberService;
    private final AdminLessonService adminLessonService;
    private final AdminBoardService adminBoardService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {

        List<MemberResponseDto> members = adminMemberService.getAllMembers();
        model.addAttribute("members", members);

        List<LessonResponseDto> lessons = adminLessonService.getAllLessons();
        model.addAttribute("lessons", lessons);

        List<BoardResponseDto> boards = adminBoardService.findAllByOrderByIdDesc();
        model.addAttribute("boards", boards);

        return "admin";
    }

}
