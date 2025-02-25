package com.health.yogiodigym.member.controller.view;

import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/regist")
    public String regist(@ModelAttribute RegistMemberDto registMemberDto, @RequestParam(value = "profile", required = false) MultipartFile profile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String registResult = null;
        if(authentication != null && (authentication.getPrincipal() instanceof MemberOAuth2User principal)){
            registResult = memberService.registWithOAuth2(registMemberDto, principal);
        }else{
            registResult = memberService.registWithEmail(registMemberDto, profile);
        }

        log.info(registMemberDto.toString());
        return registResult;
    }
}
