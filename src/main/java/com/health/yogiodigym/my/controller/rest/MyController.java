package com.health.yogiodigym.my.controller.rest;

import com.health.yogiodigym.board.dto.BoardDto.BoardDetailDto;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.LessonDto.LessonSearchDto;
import com.health.yogiodigym.lesson.service.LessonService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@Slf4j
@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {

    private final BoardService boardService;
    private final LessonService lessonService;
    private final MemberService memberService;
    private final NCPStorageService ncpStorageService;


    @PostMapping("/pwd")
    public ResponseEntity<?> pwd(@RequestBody Map<String, String> checkPwd, @AuthenticationPrincipal MemberOAuth2User principal) {
        memberService.checkPassword(checkPwd.get("pwd"), principal.getPassword());

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, PASSWORD_MATCH_SUCCESS.getMessage(), null));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody(required = false) Map<String, String> checkPwd,
                                        HttpServletRequest request, HttpServletResponse response) {
        String pwd = checkPwd.get("pwd");
        log.info("withdrawal input: {}", pwd);

        memberService.registwithdrawal(pwd, request, response);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, WITHDRAWAL_SUCCESS.getMessage(), null));
    }

    @PutMapping("/info")
    public ResponseEntity<?> info(@Valid @RequestBody UpdateMemberDto updateMemberDto,
                                  HttpServletRequest request, HttpServletResponse response) {
        log.info("update member: {}", updateMemberDto);

        memberService.updateMember(updateMemberDto, request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MEMBER_UPDATE_SUCCESS.getMessage(), null));
    }

    @PutMapping("/oauthinfo")
    public ResponseEntity<?> oauthinfo(@Valid @RequestBody UpdateOAuthMemberDto updateOAuthMemberDto,
                                       HttpServletRequest request, HttpServletResponse response) {
        log.info("update member: {}", updateOAuthMemberDto);

        memberService.updateOAuthMember(updateOAuthMemberDto, request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, MEMBER_UPDATE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/profile")
    public ResponseEntity<?> profile(@RequestParam(value = "profile") MultipartFile profile,
                                     @AuthenticationPrincipal MemberOAuth2User principal,
                                     HttpServletRequest request, HttpServletResponse response) {
        ncpStorageService.deleteImageByUrl(principal.getMember().getProfile());
        String saveFileURL = ncpStorageService.uploadImage(profile, NCPStorageServiceImpl.DirectoryPath.PROFILE);

        memberService.updateProfile(saveFileURL, principal.getMember().getId(), request, response);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, PROFILE_UPDATE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/master")
    public ResponseEntity<?> master(@RequestParam("certificate") MultipartFile[] certificate) {

        memberService.enrollMaster(certificate);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ENROLL_MASTER_SUCCESS.getMessage(), null));
    }

    @GetMapping("/board")
    public ResponseEntity<?> searchMyBoard(@RequestParam(required = false) String boardKeyword,
                                           @RequestParam(required = false) String searchColumn,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @AuthenticationPrincipal MemberOAuth2User loginUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<BoardDetailDto> boards = boardService.searchMyBoards(loginUser.getMember().getId(), boardKeyword, searchColumn, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_BOARD_SUCCESS.getMessage(), boards));
    }

    @GetMapping("/lesson")
    public ResponseEntity<?> searchLessons(@RequestParam(required = false) String lessonKeyword,
                                           @RequestParam(required = false) String searchColumn,
                                           @RequestParam(required = false) Integer days,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @AuthenticationPrincipal MemberOAuth2User loginUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<LessonSearchDto> lessons = lessonService.searchMyLessons(loginUser.getMember().getId(), lessonKeyword, searchColumn, days, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_LESSON_SUCCESS.getMessage(), lessons));
    }
}
