package com.health.yogiodigym.gym.controller;

import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GymViewController {

    private final MemberRepository memberRepository;

    @GetMapping("/gyms")
    public String gym(Model model) {
        model.addAttribute("member", memberRepository.findByEmail("chulsoo@naver.com"));

        return "gym/gyms";
    }
}
