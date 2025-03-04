package com.health.yogiodigym.member.controller.view;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        return "dashboard";
    }

//    @GetMapping("/dashboard")
//    public String dashoboard(HttpServletRequest request, Model model) {
//        String currentUrl = request.getRequestURI();
//        model.addAttribute("currentUrl", currentUrl);
//        return "dashboard";
//    }
}
