package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminViewController {

    private final MemberService memberService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {

        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);

        return "admin";
    }

}
