package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.dto.LessonDto;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.admin.service.service.AdminMemberService;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.member.entity.Member;
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

        List<Member> members = adminMemberService.getAllMembers();
        model.addAttribute("members", members);

        List<LessonDto.LessonResponseDto> lessons = adminLessonService.getAllLessons();
        model.addAttribute("lessons", lessons);

        return "admin";
    }

}
