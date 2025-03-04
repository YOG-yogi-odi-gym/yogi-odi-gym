package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.service.service.AdminBoardService;
import com.health.yogiodigym.admin.service.service.AdminCategoryService;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.admin.service.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class AdminViewController {

    private final AdminMemberService adminMemberService;
    private final AdminLessonService adminLessonService;
    private final AdminBoardService adminBoardService;
    private final AdminCategoryService adminCategoryService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {

        model.addAttribute("members", adminMemberService.getAllMembers());
        model.addAttribute("lessons", adminLessonService.getAllLessons());
        model.addAttribute("boards", adminBoardService.findAllByOrderByIdDesc());
        model.addAttribute("categories", adminCategoryService.findAll());

        return "admin/admin";
    }

}
