package com.health.yogiodigym.admin.controller;

import com.health.yogiodigym.admin.dto.MemberDto;
import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;
import com.health.yogiodigym.admin.service.AdminService;
import com.health.yogiodigym.common.response.HttpResponseDto;
import com.health.yogiodigym.member.auth.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/member/delete")
    public ResponseEntity<?> deleteMember(@RequestBody MemberDto.MemberDeleteRequestDto requestDto) {
        try {
            List<Long> memberIds = requestDto.getMemberIds();
            MemberStatus status = MemberStatus.INACTIVE;
            adminService.deleteMembers(memberIds, status);
            return ResponseEntity.ok().body(new HttpResponseDto(HttpStatus.OK.value(), "회원 삭제 완료"));
        } catch (Exception e) {
            e.printStackTrace();  // 예외 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 삭제 실패" + e.getMessage());
        }
    }
}
