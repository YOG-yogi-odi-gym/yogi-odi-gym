package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.my.dto.UpdateMemberDto;
import com.health.yogiodigym.my.dto.UpdateOAuthMemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface MemberService {

    void registWithOAuth2(RegistOAuthMemberDto registOAuthMemberDto, String saveFileURL);

    void registWithEmail(RegistMemberDto registMemberDto, String saveFileURL);

    void updateAuthentication(String email, HttpServletRequest request, HttpServletResponse response);

    void checkPassword(String pwd, String principalPwd);

    void registwithdrawal(Long id, HttpServletRequest request, HttpServletResponse response);

    void withdrawInactiveMembers();

    void updateProfile(String profile, Long id);

    void updateMember(UpdateMemberDto updateMemberDto);

    void updateOAuthMember(UpdateOAuthMemberDto updateOAuthMemberDto);

    void enrollMaster(MultipartFile[] certificate);
}