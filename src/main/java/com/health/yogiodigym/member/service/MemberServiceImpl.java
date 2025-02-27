package com.health.yogiodigym.member.service;

import com.health.yogiodigym.common.exception.MemberExistException;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.EnumSet;

import static com.health.yogiodigym.member.auth.MemberStatus.ACTIVE;
import static com.health.yogiodigym.member.auth.Role.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberDetailsService memberDetailsService;
    private final FileService fileService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Override
    public void registWithEmail(RegistMemberDto registMemberDto, MultipartFile profile) {
        memberRepository.findByEmail(registMemberDto.getEmail()).ifPresent(member -> {
            throw new MemberExistException();
        });

        String saveFileURL = fileService.saveImage(registMemberDto.getEmail(), profile);
        insertMember(registMemberDto, saveFileURL);
    }

    private void insertMember(RegistMemberDto registMemberDto, String saveFileURL) {
        String encodedPassword = null;
        if (registMemberDto.getPwd() != null && !registMemberDto.getPwd().isEmpty()) {
            encodedPassword = passwordEncoder.encode(registMemberDto.getPwd());
        }

        Member registMember = Member.builder()
                .name(registMemberDto.getName())
                .email(registMemberDto.getEmail())
                .pwd(encodedPassword)
                .gender(registMemberDto.getGender())
                .weight(registMemberDto.getWeight())
                .height(registMemberDto.getHeight())
                .addr(registMemberDto.getAddr())
                .latitude(registMemberDto.getLatitude())
                .longitude(registMemberDto.getLongitude())
                .joinDate(LocalDate.now())
                .profile(saveFileURL)
                .status(ACTIVE)
                .roles(EnumSet.of(ROLE_USER))
                .build();

        memberRepository.save(registMember);
    }

    @Override
    public void registWithOAuth2(RegistOAuthMemberDto registOAuthMemberDto, MultipartFile profile) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String saveFileURL = fileService.saveImage(registOAuthMemberDto.getEmail(), profile);
        if (saveFileURL == null) {
            saveFileURL = principal.getMember().getProfile();
        }

        insertOAuth2Member(registOAuthMemberDto, saveFileURL);
    }

    private void insertOAuth2Member(RegistOAuthMemberDto registOAuthMemberDto, String saveFileURL) {
        Member registMember = Member.builder()
                .name(registOAuthMemberDto.getName())
                .email(registOAuthMemberDto.getEmail())
                .gender(registOAuthMemberDto.getGender())
                .weight(registOAuthMemberDto.getWeight())
                .height(registOAuthMemberDto.getHeight())
                .addr(registOAuthMemberDto.getAddr())
                .latitude(registOAuthMemberDto.getLatitude())
                .longitude(registOAuthMemberDto.getLongitude())
                .joinDate(LocalDate.now())
                .profile(saveFileURL)
                .status(ACTIVE)
                .roles(EnumSet.of(ROLE_USER))
                .build();

        memberRepository.save(registMember);
    }

    @Override
    public void updateAuthentication(String email, HttpServletRequest request, HttpServletResponse response) {
        MemberOAuth2User updatePrincipal = (MemberOAuth2User) memberDetailsService.loadUserByUsername(email);

        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
                updatePrincipal,
                updatePrincipal.getPassword(),
                updatePrincipal.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(updatedAuthentication);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);
    }
}