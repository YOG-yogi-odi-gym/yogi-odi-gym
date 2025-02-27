package com.health.yogiodigym.member.controller.rest;

import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.health.yogiodigym.common.message.ErrorMessage.REGIST_MEMBER_ERROR;
import static com.health.yogiodigym.common.message.SuccessMessage.REGIST_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/regist")
    public ResponseEntity<HttpResponse> regist(Authentication authentication,
                                               @Valid @ModelAttribute RegistMemberDto registMemberDto, BindingResult bindingResult,
                                               @RequestParam(value = "profile", required = false) MultipartFile profile,
                                               HttpServletRequest request, HttpServletResponse response) {

        if (authentication != null && authentication.isAuthenticated()) {
            bindingResult.rejectValue("pwd", "ignored", "소셜로그인 사용자는 비밀번호 입력이 필요하지 않습니다.");
            bindingResult.rejectValue("pwd2", "ignored", "소셜로그인 사용자는 비밀번호 입력이 필요하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(
                    new HttpResponse(HttpStatus.BAD_REQUEST, REGIST_MEMBER_ERROR.getMessage(), bindingResult.getAllErrors())
            );
        }

        if (authentication != null && (authentication.getPrincipal() instanceof MemberOAuth2User principal)) {
            memberService.registWithOAuth2(registMemberDto, principal);
        } else {
            memberService.registWithEmail(registMemberDto, profile);
        }

        memberService.updateAuthentication(registMemberDto.getEmail(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, REGIST_SUCCESS.getMessage(), null));
    }
}
