package com.health.yogiodigym.my.controller.view;

import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import static com.health.yogiodigym.member.auth.Role.ROLE_ADMIN;
import static com.health.yogiodigym.member.auth.Role.ROLE_MASTER;

@Slf4j
@Controller
@RequestMapping("/my")
public class MyViewController {
    @GetMapping("/info")
    public String info(@AuthenticationPrincipal MemberOAuth2User principal, Model model) {
        Set<Role> roles =  principal.getMember().getRoles();

        if(roles.contains(ROLE_ADMIN)){
            model.addAttribute("role", "관리자");
        }else if(roles.contains(ROLE_MASTER)){
            model.addAttribute("role", "강사");
        }else{
            model.addAttribute("role", "회원");
        }

        return "my/info";
    }
}
