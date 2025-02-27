package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void registWithOAuth2(RegistOAuthMemberDto registOAuthMemberDto, MultipartFile profile);

    void registWithEmail(RegistMemberDto registMemberDto, MultipartFile profile);

    void updateAuthentication(String email, HttpServletRequest request, HttpServletResponse response);
}