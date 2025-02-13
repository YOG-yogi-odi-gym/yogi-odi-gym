package com.health.yogiodigym.auth.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberViewController {
    @GetMapping("/")
    public String main(){
        return "main";
    }
}
