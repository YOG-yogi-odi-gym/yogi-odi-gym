package com.health.yogiodigym.admin.controller;

import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;
import com.health.yogiodigym.admin.service.AdminService;
import com.health.yogiodigym.common.response.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/member/search")
    public ResponseEntity<?> findMembers(@RequestParam String keyword, Model model) {
        List<MemberResponseDto> members = adminService.searchMembers(keyword);
        model.addAttribute("members", members);


        return ResponseEntity.ok().body(new HttpResponseDto(HttpStatus.OK.value(), members));
    }

}
