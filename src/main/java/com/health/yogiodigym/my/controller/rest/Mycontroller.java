package com.health.yogiodigym.my.controller.rest;

import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.service.MemberService;
import com.health.yogiodigym.member.service.NCPStorageService;
import com.health.yogiodigym.member.service.impl.NCPStorageServiceImpl;
import com.health.yogiodigym.my.dto.UpdateMemberDto;
import com.health.yogiodigym.my.dto.UpdateOAuthMemberDto;
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

import java.util.*;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class Mycontroller {

    private final MemberService memberService;
    private final NCPStorageService ncpStorageService;


    @PostMapping("/pwd")
    public ResponseEntity<?> pwd(@RequestBody Map<String, String> checkPwd, @AuthenticationPrincipal MemberOAuth2User principal) {
        memberService.checkPassword(checkPwd.get("pwd"), principal.getPassword());

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, null, null));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody Map<String, String> checkPwd, @AuthenticationPrincipal MemberOAuth2User principal,
                                        HttpServletRequest request, HttpServletResponse response) {
        memberService.checkPassword(checkPwd.get("pwd"), principal.getPassword());
        memberService.registwithdrawal(principal.getMember().getId(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, WITHDRAWAL_SUCCESS.getMessage(), null));
    }

    @PutMapping("/info")
    public ResponseEntity<?> info(@Valid @RequestBody UpdateMemberDto updateMemberDto, @AuthenticationPrincipal MemberOAuth2User principal,
                                        HttpServletRequest request, HttpServletResponse response) {
        log.info("update member: {}", updateMemberDto);

        memberService.updateMember(updateMemberDto);
        memberService.updateAuthentication(principal.getMember().getEmail(), request, response);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MEMBER_UPDATE_SUCCESS.getMessage(), null));
    }

    @PutMapping("/oauthinfo")
    public ResponseEntity<?> oauthinfo(@Valid @RequestBody UpdateOAuthMemberDto updateOAuthMemberDto, @AuthenticationPrincipal MemberOAuth2User principal,
                                       HttpServletRequest request, HttpServletResponse response) {
        log.info("update member: {}", updateOAuthMemberDto);

        memberService.updateOAuthMember(updateOAuthMemberDto);
        memberService.updateAuthentication(principal.getMember().getEmail(), request, response);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MEMBER_UPDATE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/profile")
    public ResponseEntity<?> profile(@RequestParam(value = "profile") MultipartFile profile,
                                     @AuthenticationPrincipal MemberOAuth2User principal,
                                     HttpServletRequest request, HttpServletResponse response){
        ncpStorageService.deleteImageByUrl(principal.getMember().getProfile());
        String saveFileURL = ncpStorageService.uploadImage(profile, NCPStorageServiceImpl.DirectoryPath.PROFILE);
        memberService.updateProfile(saveFileURL, principal.getMember().getId());
        memberService.updateAuthentication(principal.getMember().getEmail(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, PROFILE_UPDATE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/master")
    public ResponseEntity<?> master(@RequestParam("certificate") MultipartFile[] certificate) {
        Set<String> certificates = new HashSet<>();
        for (MultipartFile file : certificate) {
            if (!file.isEmpty()) {
                certificates.add(ncpStorageService.uploadImage(file, NCPStorageServiceImpl.DirectoryPath.CERTIFICATE));
            }
        }
        memberService.enrollMaster(certificates);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ENROLL_MASTER_SUCCESS.getMessage(), null));
    }
}
