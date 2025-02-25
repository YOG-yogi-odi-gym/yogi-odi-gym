package com.health.yogiodigym.gym.controller;

import com.health.yogiodigym.member.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GymViewController {

    private final MemberRepository memberRepository;

    public GymViewController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/gyms")
    public ResponseEntity<?> gym(Model model) {
        model.addAttribute("member", memberRepository.findByEmail("test@test.com"));
        return ResponseEntity.ok("gym/gyms");
    }
}
