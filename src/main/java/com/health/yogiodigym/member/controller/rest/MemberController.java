package com.health.yogiodigym.member.controller.rest;

import com.health.yogiodigym.common.exception.CodeNotMatchException;
import com.health.yogiodigym.common.exception.EmailNotFoundException;
import com.health.yogiodigym.common.exception.MemberExistException;
import com.health.yogiodigym.common.exception.SocialMemberPwdChangeException;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.member.dto.EmailVerifyDto;
import com.health.yogiodigym.member.dto.PasswordChangeDto;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.service.MemberService;
import com.health.yogiodigym.member.service.NCPStorageService;
import com.health.yogiodigym.member.service.impl.NCPStorageServiceImpl;
import com.health.yogiodigym.member.service.impl.RedisEmailcodeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final NCPStorageService ncpStorageService;
    private final RedisEmailcodeServiceImpl redisEmailcodeService;

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@Valid @ModelAttribute RegistMemberDto registMemberDto,
                                    @RequestParam(value = "profile", required = false) MultipartFile profile,
                                    HttpServletRequest request, HttpServletResponse response) {

        log.info("regist input: {}", registMemberDto.toString());
        log.info("regist inputFile : {}", profile.isEmpty());

        String saveFileURL = ncpStorageService.uploadImage(profile, NCPStorageServiceImpl.DirectoryPath.PROFILE);

        memberService.registWithEmail(registMemberDto, saveFileURL);

        memberService.updateAuthentication(registMemberDto.getEmail(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, REGIST_SUCCESS.getMessage(), null));
    }

    @PostMapping("/auth-regist")
    public ResponseEntity<?> authRegist(@AuthenticationPrincipal MemberOAuth2User principal,
                                        @Valid @ModelAttribute RegistOAuthMemberDto registOAuthMemberDto,
                                        @RequestParam(value = "profile", required = false) MultipartFile profile,
                                        HttpServletRequest request, HttpServletResponse response) {

        log.info("auth-regist input: {}", registOAuthMemberDto.toString());
        log.info("auth-regist inputFile : {}", profile.isEmpty());

        String saveFileURL = ncpStorageService.uploadImage(profile, NCPStorageServiceImpl.DirectoryPath.PROFILE);
        if (saveFileURL == null) {
            saveFileURL = principal.getMember().getProfile();
        }

        memberService.registWithOAuth2(registOAuthMemberDto, saveFileURL);

        memberService.updateAuthentication(registOAuthMemberDto.getEmail(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, REGIST_SUCCESS.getMessage(), null));
    }

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@Valid @RequestBody EmailVerifyDto emailVerifyDto) {
        log.info("send-code input: {}", emailVerifyDto.toString());

        Optional<Member> joinedMember = memberService.checkJoined(emailVerifyDto.getEmail());
        if(joinedMember.isPresent()){
            throw new MemberExistException();
        }

        memberService.emailAuthentication(emailVerifyDto.getEmail());

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEND_MAILCODE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/find-pwd")
    public ResponseEntity<?> findPwd(@Valid @RequestBody EmailVerifyDto emailVerifyDto) {
        log.info("find-pwd input: {}", emailVerifyDto.toString());

        Optional<Member> joinedMember = memberService.checkJoined(emailVerifyDto.getEmail());
        if(joinedMember.isEmpty()){
            throw new EmailNotFoundException();
        }

        if(joinedMember.get().getPwd() == null || joinedMember.get().getPwd().isEmpty()){
            throw new SocialMemberPwdChangeException();
        }

        memberService.emailAuthentication(emailVerifyDto.getEmail());

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEND_MAILCODE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/mail-verify")
    public ResponseEntity<?> mailVerify(@Valid @RequestBody EmailVerifyDto emailVerifyDto) {
        log.info("mail-verify input: {}", emailVerifyDto.toString());

        String code = emailVerifyDto.getCode();
        String email = emailVerifyDto.getEmail();
        String redisCode = redisEmailcodeService.getCode(email);

        log.info("redisCode = {}", redisCode);

        if (!code.equals(redisCode)) {
            throw new CodeNotMatchException();
        }

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MAIL_VERIFY_SUCCESS.getMessage(), null));
    }

    @PutMapping("/pwd-change")
    public ResponseEntity<?> pwdChange(@Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        log.info("pwd-change input: {}", passwordChangeDto.toString());

        if (memberService.checkJoined(passwordChangeDto.getEmail()).isEmpty()) {
            throw new EmailNotFoundException();
        }

        memberService.pwdChange(passwordChangeDto);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, PASSWORD_CHANGE_SUCCESS.getMessage(), null));
    }
}
