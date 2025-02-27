package com.health.yogiodigym.gym.controller;

import com.health.yogiodigym.admin.dto.MemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.repository.MemberRepository;
import com.health.yogiodigym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GymViewController {

    @GetMapping("/gyms")
    public String gym(Model model,
                      @AuthenticationPrincipal MemberOAuth2User loginUser) {
        Member loginMember = loginUser.getMember();

        model.addAttribute("member", loginMember);

        return "gym/gyms";
    }
}
