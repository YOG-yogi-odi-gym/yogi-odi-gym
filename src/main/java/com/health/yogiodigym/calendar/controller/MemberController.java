package com.health.yogiodigym.calendar.controller;

//import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.service.MemberService;
import com.health.yogiodigym.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletRequest request,
                        Model model) {
        boolean isAuthenticated = memberService.login(email, password);
        if (isAuthenticated) {
            HttpSession session = request.getSession();

            Member member = memberService.getMemberByEmail(email);

            session.setAttribute("userEmail", member.getEmail()); // 세션 저장
            session.setAttribute("memberId", member.getId());
            session.setAttribute("userWeight", member.getWeight());

            return "redirect:/test";

        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 종료
        }
        return "redirect:/login";
    }

    @GetMapping("/test")
    public String testPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            return "redirect:/login";
        }
        model.addAttribute("userEmail", session.getAttribute("userEmail"));
        model.addAttribute("memberId", session.getAttribute("memberId"));
        model.addAttribute("userWeight", session.getAttribute("userWeight"));

        return "test";

    }


}
