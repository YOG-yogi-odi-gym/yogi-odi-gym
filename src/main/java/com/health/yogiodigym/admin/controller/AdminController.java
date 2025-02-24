package com.health.yogiodigym.admin.controller;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.AdminService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/member/search")
    public ResponseEntity<?> findMembers(@RequestParam String keyword) {
        List<MemberResponseDto> members = adminService.searchMembers(keyword);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MEMBER_SEARCH_SUCCESS.getMessage(), members));
    }

    @PostMapping("/member/inactive")
    public ResponseEntity<?> setInactiveStatus(@RequestBody List<Long> memberIds) {
        adminService.setInactiveStatus(memberIds);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MEMBER_STATUS_CHANGE_SUCCESS.getMessage(), null));
    }
}
