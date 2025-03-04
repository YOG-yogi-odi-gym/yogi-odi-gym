package com.health.yogiodigym.member.service.impl;

import com.health.yogiodigym.common.exception.MemberExistException;
import com.health.yogiodigym.common.exception.WrongPasswordException;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.repository.MemberRepository;
import com.health.yogiodigym.member.service.MemberService;
import com.health.yogiodigym.member.service.NCPStorageService;
import com.health.yogiodigym.my.dto.UpdateMemberDto;
import com.health.yogiodigym.my.dto.UpdateOAuthMemberDto;
import com.health.yogiodigym.my.entity.MemberToMaster;
import com.health.yogiodigym.my.repository.MemberToMasterRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static com.health.yogiodigym.member.auth.Role.ROLE_USER;
import static com.health.yogiodigym.member.status.EnrollMasterStatus.WAIT;
import static com.health.yogiodigym.member.status.MemberStatus.ACTIVE;
import static com.health.yogiodigym.member.status.MemberStatus.INACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberToMasterRepository memberToMasterRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberDetailsService memberDetailsService;
    private final NCPStorageService ncpStorageService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Override
    public void registWithEmail(RegistMemberDto registMemberDto, String saveFileURL) {
        memberRepository.findByEmail(registMemberDto.getEmail()).ifPresent(member -> {
            throw new MemberExistException();
        });

        insertMember(registMemberDto, saveFileURL);
    }

    @Override
    public void enrollMaster(MultipartFile[] certificate) {
        Set<String> certificates = new HashSet<>();
        for (MultipartFile file : certificate) {
            addCertificateURL(file,certificates);
        }

        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MemberToMaster addEnrollMaster = MemberToMaster.builder()
                .member(principal.getMember())
                .enrollDate(LocalDate.now())
                .approvalStatus(WAIT)
                .certificate(certificates)
                .build();

        memberToMasterRepository.save(addEnrollMaster);
    }

    private void addCertificateURL(MultipartFile file, Set<String> certificates) {
        if(!file.isEmpty()) {
            certificates.add(ncpStorageService.uploadImage(file, NCPStorageServiceImpl.DirectoryPath.CERTIFICATE));
        }
    }

    @Override
    public void updateMember(UpdateMemberDto updateMemberDto) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member currentMember = principal.getMember();
        currentMember.updateMember(updateMemberDto);

        memberRepository.save(currentMember);
    }

    @Override
    public void updateOAuthMember(UpdateOAuthMemberDto updateOAuthMemberDto) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member currentMember = principal.getMember();
        currentMember.updateOAuthMember(updateOAuthMemberDto);

        memberRepository.save(currentMember);
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
    public void registWithOAuth2(RegistOAuthMemberDto registOAuthMemberDto, String saveFileURL) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

    @Override
    public void checkPassword(String pwd, String principalPwd) {
        if(!passwordEncoder.matches(pwd, principalPwd)){
            throw new WrongPasswordException();
        }
    }

    @Override
    public void registwithdrawal(Long id, HttpServletRequest request, HttpServletResponse response) {
        memberRepository.setStatusInactive(INACTIVE, LocalDate.now(), id);
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public void withdrawInactiveMembers() {
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        int deletedCount = memberRepository.deleteByDropDateBeforeAndStatus(threeDaysAgo, INACTIVE);
        log.info("Deleted members : {}", deletedCount);
    }

    @Override
    public void updateProfile(String profile, Long id) {
        memberRepository.setProfile(profile, id);
    }
}