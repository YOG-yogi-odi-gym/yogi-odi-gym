package com.health.yogiodigym.member.controller.view;

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
        if(authentication != null && !(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/dashboard";
        }else{
            return "redirect:/member/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashoboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof OAuth2User) {
            MemberOAuth2User oauth2User = (MemberOAuth2User) authentication.getPrincipal();
            Map<String, Object> profile = oauth2User.getAttribute("profile");

            model.addAttribute("nickname", oauth2User.getMember().getName());
            model.addAttribute("email", oauth2User.getMember().getEmail());
            model.addAttribute("thumbnail_image", oauth2User.getMember().getProfile());
            return "/dashboard";
        } else if (authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return "/dashboard";
        }
        return "/member/login";
    }

    @GetMapping("/denied")
    public String denied(){
        return "/denied";
    }
}
