package com.health.yogiodigym.member.controller.view;

import com.health.yogiodigym.member.entity.MemberOAuth2User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

    @GetMapping("/")
    public String main(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/member/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashoboard() {
        return "/dashboard";
    }
}
