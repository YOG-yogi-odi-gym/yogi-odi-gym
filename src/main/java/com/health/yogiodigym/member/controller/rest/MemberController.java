package com.health.yogiodigym.member.controller.rest;

import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

import static com.health.yogiodigym.common.message.ErrorMessage.REGIST_MEMBER_ERROR;
import static com.health.yogiodigym.common.message.SuccessMessage.REGIST_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/regist")
    public ResponseEntity<HttpResponse> regist(@Valid @ModelAttribute RegistMemberDto registMemberDto, BindingResult bindingResult,
                                               @RequestParam(value = "profile", required = false) MultipartFile profile,
                                               HttpServletRequest request, HttpServletResponse response) {

        log.info("일반회원가입DTO : "+registMemberDto.toString());

        if(bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(
                    new HttpResponse(HttpStatus.BAD_REQUEST, REGIST_MEMBER_ERROR.getMessage(), sortError(bindingResult, false))
            );
        }

        memberService.registWithEmail(registMemberDto, profile);
        memberService.updateAuthentication(registMemberDto.getEmail(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, REGIST_SUCCESS.getMessage(), null));
    }

    @PostMapping("/oAuthRegist")
    public ResponseEntity<HttpResponse> oAuthRegist(Authentication authentication,
                                                    @Valid @ModelAttribute RegistOAuthMemberDto registOAuthMemberDto, BindingResult bindingResult,
                                                    @RequestParam(value = "profile", required = false) MultipartFile profile,
                                                    HttpServletRequest request, HttpServletResponse response){

        log.info("소셜회원가입DTO : "+registOAuthMemberDto.toString());

        if(bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(
                    new HttpResponse(HttpStatus.BAD_REQUEST, REGIST_MEMBER_ERROR.getMessage(), sortError(bindingResult, true))
            );
        }

        memberService.registWithOAuth2(registOAuthMemberDto, profile);
        memberService.updateAuthentication(registOAuthMemberDto.getEmail(), request, response);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, REGIST_SUCCESS.getMessage(), null));
    }

    private List<ObjectError> sortError(BindingResult bindingResult, boolean oAuth) {
        List<String> fieldOrder;
        if(oAuth){
            fieldOrder = List.of("name", "email", "gender", "height", "weight", "latitude");
        }else{
            fieldOrder = List.of("name", "email", "pwd", "pwd2", "passwordMatching", "gender", "height", "weight", "latitude");
        }

        return bindingResult.getAllErrors().stream()
                .sorted(Comparator.comparingInt(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldOrder.indexOf(fieldError.getField());
                    }
                    return Integer.MAX_VALUE;
                })).toList();
    }
}
