package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.dto.EmailVerifyDto;
import com.health.yogiodigym.member.dto.PasswordChangeDto;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.my.dto.UpdateMemberDto;
import com.health.yogiodigym.my.dto.UpdateOAuthMemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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

    void pwdChange(PasswordChangeDto passwordChangeDto);

    void findPwd(EmailVerifyDto emailVerifyDto);

    void sendCode(EmailVerifyDto emailVerifyDto);
}