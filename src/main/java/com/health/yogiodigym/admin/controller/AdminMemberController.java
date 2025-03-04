package com.health.yogiodigym.admin.controller;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.service.AdminMemberService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/member")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping("/search")
    public ResponseEntity<?> searchMembers(@RequestParam String memberKeyword) {
        List<MemberResponseDto> members = adminMemberService.searchMembers(memberKeyword);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ADMIN_MEMBER_SEARCH_SUCCESS.getMessage(), members));
    }

    @PostMapping("/inactive")
    public ResponseEntity<?> setInactiveStatus(@RequestBody List<Long> memberIds) {

        adminMemberService.setInactiveStatus(memberIds);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ADMIN_MEMBER_STATUS_CHANGE_SUCCESS.getMessage(), null));
    }
}
