package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface MemberService {

    void registWithOAuth2(RegistMemberDto registMemberDto, MemberOAuth2User principal);

    void registWithEmail(RegistMemberDto registMemberDto, MultipartFile profile);

    void updateAuthentication(String email, HttpServletRequest request, HttpServletResponse response);

}