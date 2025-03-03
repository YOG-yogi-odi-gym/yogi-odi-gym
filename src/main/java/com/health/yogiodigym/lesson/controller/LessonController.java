package com.health.yogiodigym.lesson.controller;

import com.health.yogiodigym.chat.service.ChatRoomService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.LessonDto.*;
import com.health.yogiodigym.lesson.service.LessonEnrollmentService;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final ChatRoomService chatRoomService;
    private final LessonEnrollmentService lessonEnrollmentService;

    @GetMapping("/search")
    public ResponseEntity<?> searchLessons(@RequestParam(required = false) String lessonKeyword,
                                           @RequestParam(required = false) String searchColumn,
                                           @RequestParam(required = false) Integer days,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id"));

        Page<LessonSearchDto> lessons = lessonService.searchLessons(lessonKeyword, searchColumn, days, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_LESSON_SUCCESS.getMessage(), lessons));
    }

    @PostMapping("/register")
    public RedirectView registerLesson(@ModelAttribute LessonRequestDto lessonDto,
                                       @AuthenticationPrincipal MemberOAuth2User loginUser) {
        Member loginMember = loginUser.getMember();
        lessonService.registerLesson(lessonDto, loginMember);

        return new RedirectView("/lesson");
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollLesson(@RequestBody LessonEnrollmentDto request) {
        boolean success = lessonEnrollmentService.enrollLesson(request.getMemberId(), request.getLessonId());

        return ResponseEntity.ok().body(
                new HttpResponse(HttpStatus.OK, "수업 등록 성공", Map.of("success", success))
        );
    }

    @DeleteMapping("/cancel/{memberId}/{lessonId}")
    public ResponseEntity<?> cancelEnrollment(@PathVariable Long memberId, @PathVariable Long lessonId) {
        boolean success = lessonEnrollmentService.cancelEnrollment(memberId, lessonId);

        return ResponseEntity.ok().body(
                new HttpResponse(HttpStatus.OK, "수업 등록 취소 성공", Map.of("success", success))
        );
    }

    @GetMapping("/enrolled")
    public ResponseEntity<?> isEnrolled(@RequestParam Long lessonId, @RequestParam Long memberId) {
        boolean enrolled = lessonEnrollmentService.isUserEnrolled(memberId, lessonId);

        return ResponseEntity.ok().body(
                new HttpResponse(HttpStatus.OK, "수업 등록 여부 조회 성공", Map.of("enrolled", enrolled))
        );
    }

    @PostMapping("/edit")
    public RedirectView editLesson(@ModelAttribute LessonEditDto lessonDto) {
        lessonService.editLesson(lessonDto);
        return new RedirectView("/lesson/" + lessonDto.getId());
    }

}