package com.health.yogiodigym.auth.controller.rest;

import com.health.yogiodigym.auth.entity.Member;
import com.health.yogiodigym.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member")
    public List<Member> findMember(Long teamId){
        return memberService.findMember(teamId);
    }
}
