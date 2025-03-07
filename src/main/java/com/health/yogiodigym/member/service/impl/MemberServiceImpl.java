package com.health.yogiodigym.member.service.impl;

import com.health.yogiodigym.common.exception.*;
import com.health.yogiodigym.member.dto.EmailVerifyDto;
import com.health.yogiodigym.member.dto.PasswordChangeDto;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.dto.RegistOAuthMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.repository.MemberRepository;
import com.health.yogiodigym.member.service.EmailService;
import com.health.yogiodigym.member.service.MemberService;
import com.health.yogiodigym.member.service.NCPStorageService;
import com.health.yogiodigym.member.service.RedisEmailcodeService;
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
import java.util.HashSet;
import java.util.Set;

import static com.health.yogiodigym.member.status.EnrollMasterStatus.WAIT;
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
    private final RedisEmailcodeService redisEmailcodeService;
    private final EmailService emailService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Override
    public void registWithEmail(RegistMemberDto registMemberDto, String saveFileURL, HttpServletRequest request, HttpServletResponse response) {
        memberRepository.findByEmail(registMemberDto.getEmail()).ifPresent(member -> {
            throw new MemberExistException();
        });

        insertMember(registMemberDto, saveFileURL);
        updateAuthentication(registMemberDto.getEmail(), request, response);
    }

    @Override
    public void enrollMaster(MultipartFile[] certificate) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        memberToMasterRepository.findByMemberId(principal.getMember().getId()).ifPresent(memberToMaster -> {
            throw new AlreadyEnrollMasterException();
        });

        Set<String> certificates = new HashSet<>();
        for (MultipartFile file : certificate) {
            addCertificateURL(file, certificates);
        }

        MemberToMaster addEnrollMaster = MemberToMaster.builder()
                .member(principal.getMember())
                .enrollDate(LocalDate.now())
                .approvalStatus(WAIT)
                .certificate(certificates)
                .build();

        memberToMasterRepository.save(addEnrollMaster);
    }

    private void addCertificateURL(MultipartFile file, Set<String> certificates) {
        if (!file.isEmpty()) {
            certificates.add(ncpStorageService.uploadImage(file, NCPStorageServiceImpl.DirectoryPath.CERTIFICATE));
        }
    }

    @Override
    public void updateMember(UpdateMemberDto updateMemberDto, HttpServletRequest request, HttpServletResponse response) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CheckNewPwdMatchBeforePwd(updateMemberDto.getPwd(), principal.getPassword());

        updateMemberDto.setPwd(passwordEncoder.encode(updateMemberDto.getPwd()));

        Member currentMember = principal.getMember();
        currentMember.updateMember(updateMemberDto);

        memberRepository.save(currentMember);
        updateAuthentication(principal.getMember().getEmail(), request, response);
    }

    private void CheckNewPwdMatchBeforePwd(String pwd, String beforePwd) {
        if (passwordEncoder.matches(pwd, beforePwd)) {
            throw new BeforePwdMatchException();
        }
    }

    @Override
    public void updateOAuthMember(UpdateOAuthMemberDto updateOAuthMemberDto, HttpServletRequest request, HttpServletResponse response) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member currentMember = principal.getMember();
        currentMember.updateOAuthMember(updateOAuthMemberDto);

        memberRepository.save(currentMember);
        updateAuthentication(principal.getMember().getEmail(), request, response);
    }

    @Override
    public void findPwd(EmailVerifyDto emailVerifyDto) {
        Member joinedMember = memberRepository.findByEmail(emailVerifyDto.getEmail()).orElseThrow(() -> new EmailNotFoundException());

        if(joinedMember.getPwd() == null || joinedMember.getPwd().isEmpty()){
            throw new SocialMemberPwdChangeException();
        }

        emailAuthentication(emailVerifyDto.getEmail());
    }

    @Override
    public void sendCode(EmailVerifyDto emailVerifyDto) {
        memberRepository.findByEmail(emailVerifyDto.getEmail())
                .ifPresent(member -> { throw new MemberExistException(); });

        emailAuthentication(emailVerifyDto.getEmail());
    }

    @Override
    public void pwdChange(PasswordChangeDto passwordChangeDto) {
        Member changePwdMember = memberRepository.
                findByEmail(passwordChangeDto.getEmail()).orElseThrow(() -> new EmailNotFoundException());

        String newPwd = passwordEncoder.encode(passwordChangeDto.getPwd());
        changePwdMember.setPwd(newPwd);
    }

    private void emailAuthentication(String email) {
        String code = emailService.makeCode();

        emailService.sendCodeToMail(email, code);
        redisEmailcodeService.setCode(email, code);
    }

    private void insertMember(RegistMemberDto registMemberDto, String saveFileURL) {
        registMemberDto.setPwd(encodePassword(registMemberDto.getPwd()));
        Member registMember = Member.buildRegistMember(registMemberDto, saveFileURL);

        memberRepository.save(registMember);
    }

    private String encodePassword(String originPwd) {
        if (originPwd == null || originPwd.isEmpty()) {
            throw new PasswordEmptyException();
        }

        return passwordEncoder.encode(originPwd);
    }

    @Override
    public void registWithOAuth2(RegistOAuthMemberDto registOAuthMemberDto, String saveFileURL, HttpServletRequest request, HttpServletResponse response) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (saveFileURL == null) {
            saveFileURL = principal.getMember().getProfile();
        }

        insertOAuth2Member(registOAuthMemberDto, saveFileURL);
        updateAuthentication(registOAuthMemberDto.getEmail(), request, response);
    }

    private void insertOAuth2Member(RegistOAuthMemberDto registOAuthMemberDto, String saveFileURL) {
        Member registMember = Member.buildRegistOAuthMember(registOAuthMemberDto, saveFileURL);

        memberRepository.save(registMember);
    }

    private void updateAuthentication(String email, HttpServletRequest request, HttpServletResponse response) {
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
    public void registwithdrawal(String checkPwd, HttpServletRequest request, HttpServletResponse response) {
        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (checkPwd != null && !checkPwd.isEmpty()) {
            checkPassword(checkPwd, principal.getPassword());
        }
        Long principalMemberId = principal.getMember().getId();

        Member withdrawalMember =  memberRepository.findById(principalMemberId).orElseThrow(() -> new MemberNotFoundException(principalMemberId));

        withdrawalMember.setInactive();
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public void checkPassword(String pwd, String principalPwd) {
        if (!passwordEncoder.matches(pwd, principalPwd)) {
            throw new WrongPasswordException();
        }
    }


    @Override
    public void withdrawInactiveMembers() {
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        int deletedCount = memberRepository.deleteByDropDateBeforeAndStatus(threeDaysAgo, INACTIVE);
        log.info("Deleted members : {}", deletedCount);
    }

    @Override
    public void updateProfile(String profile, Long id, HttpServletRequest request, HttpServletResponse response) {
        Member updateProfileMember = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));

        updateProfileMember.setProfile(profile);

        MemberOAuth2User principal = (MemberOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        updateAuthentication(principal.getMember().getEmail(), request, response);
    }

    @Override
    public void mailVerify(EmailVerifyDto emailVerifyDto) {
        String code = emailVerifyDto.getCode();
        String email = emailVerifyDto.getEmail();
        String redisCode = redisEmailcodeService.getCode(email);

        log.info("redisCode = {}", redisCode);

        if (!code.equals(redisCode)) {
            throw new CodeNotMatchException();
        }
    }
}