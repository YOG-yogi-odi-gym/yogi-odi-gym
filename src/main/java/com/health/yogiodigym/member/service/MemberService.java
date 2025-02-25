package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.dto.RegistMemberDto;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public String registWithOAuth2(RegistMemberDto registMemberDto, MemberOAuth2User principal){
        Member updateMember = insertMember(registMemberDto,principal.getMember().getProfile());
        authenticationService.updateAuthentication(updateMember.getEmail());

        return "registOAuth2Success";
    }

    public String registWithEmail(RegistMemberDto registMemberDto, MultipartFile profile) {
        Member existingMember = memberRepository.findByEmail(registMemberDto.getEmail()).orElse(null);
        if (existingMember != null) {
            return "existing";
        } else {
            String saveFileName = saveProfile(registMemberDto.getEmail(), profile);
            insertMember(registMemberDto, "/images/profile/"+saveFileName);
            return "registEmailSuccess";
        }
    }

    private String saveProfile(String saveFileName, MultipartFile profile) {
        String uploadDirectory = "src/main/resources/static/images/profile";
        if (!profile.isEmpty()) {
            String originName = profile.getOriginalFilename();
            String fileExtension = originName.substring(originName.lastIndexOf("."));
            String fileName = saveFileName + fileExtension;

            Path path = Paths.get(uploadDirectory, fileName);

            try {
                try (Stream<Path> files = Files.list(Paths.get(uploadDirectory))) {

                    files.filter(file -> file.getFileName().toString().startsWith(saveFileName + "."))
                            .forEach(existingFile -> {
                                try {
                                    Files.delete(existingFile);
                                    log.info("기존 파일 삭제: " + existingFile.toString());
                                } catch (IOException e) {
                                    log.warn("기존 파일 삭제 실패: " + existingFile.toString(), e);
                                }
                            });
                }

                Files.copy(profile.getInputStream(), path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            log.info("파일 저장 경로: " + path.toString());
            return fileName;
        } else {
            log.warn("업로드된 파일이 없습니다.");
            return null;
        }
    }

    private Member insertMember(RegistMemberDto registMemberDto, String saveFileName) {
        String encodedPassword = null;
        if(registMemberDto.getPwd() != null && registMemberDto.getPwd().isEmpty()){
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
                .profile(saveFileName)
                .status(MemberStatus.ACTIVE)
                .roles(EnumSet.of(Role.ROLE_USER))
                .build();
        return memberRepository.save(registMember);
    }
}
