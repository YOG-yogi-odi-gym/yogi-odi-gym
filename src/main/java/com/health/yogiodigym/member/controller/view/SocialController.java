package com.health.yogiodigym.member.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocialController {
    @GetMapping("/")
    public String main(){
        return "redirect:/member/login";
    }

    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }

    @GetMapping("/dashboard")
    public String dashoboard(){
        return "/dashboard";
    }
}
