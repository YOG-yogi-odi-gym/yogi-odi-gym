package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.dto.LessonDto.*;
import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.admin.service.service.AdminMemberService;
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

    @GetMapping("/admin")
    public String showAdminPage(Model model) {

        List<MemberResponseDto> members = adminMemberService.getAllMembers();
        model.addAttribute("members", members);

        List<LessonResponseDto> lessons = adminLessonService.getAllLessons();
        model.addAttribute("lessons", lessons);

        return "admin";
    }

}
