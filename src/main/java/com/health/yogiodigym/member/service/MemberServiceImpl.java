//package com.health.yogiodigym.member.service;
//
//import com.health.yogiodigym.common.exception.MemberExistException;
//import com.health.yogiodigym.member.dto.RegistMemberDto;
//import com.health.yogiodigym.member.entity.Member;
//import com.health.yogiodigym.member.entity.MemberOAuth2User;
//import com.health.yogiodigym.member.repository.MemberRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
//import org.springframework.security.web.context.SecurityContextRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.util.EnumSet;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static com.health.yogiodigym.member.auth.MemberStatus.ACTIVE;
//import static com.health.yogiodigym.member.auth.Role.ROLE_USER;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MemberServiceImpl implements MemberService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final MemberDetailsService memberDetailsService;
//    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
//
//    @Override
//    public void registWithOAuth2(RegistMemberDto registMemberDto, MemberOAuth2User principal) {
//        insertMember(registMemberDto, principal.getMember().getProfile());
//    }
//
//    @Override
//    public void registWithEmail(RegistMemberDto registMemberDto, MultipartFile profile) {
//        memberRepository.findByEmail(registMemberDto.getEmail()).ifPresent(member -> {
//            throw new MemberExistException();
//        });
//
//        String saveFileURL = saveImage(registMemberDto.getEmail(), profile);
//        insertMember(registMemberDto, saveFileURL);
//    }
//
//    private String saveImage(String saveFileName, MultipartFile profile) {
//        String uploadDirectory = "src/main/resources/static/images/profile";
//        if (!profile.isEmpty()) {
//            String originName = profile.getOriginalFilename();
//            String fileExtension = originName.substring(originName.lastIndexOf("."));
//            String fileName = saveFileName + fileExtension;
//
//            Path path = Paths.get(uploadDirectory, fileName);
//
//            try (Stream<Path> files = Files.list(Paths.get(uploadDirectory))) {
//                List<Path> existingFiles = files.filter(file -> file.getFileName().toString().startsWith(saveFileName + ".")).toList();
//
//                for (Path existingFile : existingFiles) {
//                    Files.delete(existingFile);
//                    log.info("기존 파일 삭제: {}", existingFile);
//                }
//
//                Files.copy(profile.getInputStream(), path);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            log.info("파일 저장 경로: " + path.toString());
//
//            return "/images/profile/" + fileName;
//        } else {
//            log.warn("업로드된 파일이 없습니다.");
//            return null;
//        }
//    }
//
//    private Member insertMember(RegistMemberDto registMemberDto, String saveFileURL) {
//        String encodedPassword = null;
//        if (registMemberDto.getPwd() != null && !registMemberDto.getPwd().isEmpty()) {
//            encodedPassword = passwordEncoder.encode(registMemberDto.getPwd());
//        }
//
//        Member registMember = Member.builder()
//                .name(registMemberDto.getName())
//                .email(registMemberDto.getEmail())
//                .pwd(encodedPassword)
//                .gender(registMemberDto.getGender())
//                .weight(registMemberDto.getWeight())
//                .height(registMemberDto.getHeight())
//                .addr(registMemberDto.getAddr())
//                .latitude(registMemberDto.getLatitude())
//                .longitude(registMemberDto.getLongitude())
//                .joinDate(LocalDate.now())
//                .profile(saveFileURL)
//                .status(ACTIVE)
//                .roles(EnumSet.of(ROLE_USER))
//                .build();
//
//        return memberRepository.save(registMember);
//    }
//
//    @Override
//    public void updateAuthentication(String email, HttpServletRequest request, HttpServletResponse response) {
//        MemberOAuth2User updatePrincipal = (MemberOAuth2User) memberDetailsService.loadUserByUsername(email);
//
//        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
//                updatePrincipal,
//                updatePrincipal.getPassword(),
//                updatePrincipal.getAuthorities()
//        );
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(updatedAuthentication);
//        SecurityContextHolder.setContext(context);
//
//        securityContextRepository.saveContext(context, request, response);
//    }
//}
