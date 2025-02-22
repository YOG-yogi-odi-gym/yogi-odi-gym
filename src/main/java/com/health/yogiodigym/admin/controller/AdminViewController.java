package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.service.AdminService;
import com.health.yogiodigym.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminViewController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {

        List<Member> members = adminService.getAllMembers();
        model.addAttribute("members", members);

        return "admin";
    }

}
