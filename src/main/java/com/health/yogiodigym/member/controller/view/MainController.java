package com.health.yogiodigym.member.controller.view;

import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@Controller
public class MainController {
    @GetMapping("/")
    public String main(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("\n\n\n\n\n"+authentication+"\n\n\n\n\n");
        if(authentication != null && !(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/dashboard";
        }else{
            return "redirect:/member/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashoboard(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof OAuth2User) {
            return "/dashboard";
        } else if (authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return "/dashboard";
        }
        return "/member/login";
    }
}
