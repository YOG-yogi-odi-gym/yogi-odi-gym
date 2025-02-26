package com.health.yogiodigym.lesson.controller;

import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.lesson.service.LessonEnrollmentService;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

import static com.health.yogiodigym.common.message.SuccessMessage.SEARCH_GYMS_SUCCESS;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final MemberRepository memberRepository;
    private final LessonEnrollmentService lessonEnrollmentService;

    @GetMapping("/search")
    public ResponseEntity<?> searchLessons(@RequestParam(required = false) String lessonKeyword,
                                           @RequestParam(required = false) String searchColumn,
                                           @RequestParam(required = false) Integer days,
                                           @RequestParam(required = false) List<Long> categories,
                                           @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<LessonDto> lessons = lessonService.searchLessons(lessonKeyword, searchColumn, days, categories, pageable);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_GYMS_SUCCESS.getMessage(), lessons));
    }

    @PostMapping("/register")
    public RedirectView registerLesson(@ModelAttribute LessonDto.Request lessonDto) {
        Member master = memberRepository.findByEmail("chulsoo@naver.com");
        lessonService.registerLesson(lessonDto, master);

        return new RedirectView("/lesson");
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollLesson(@RequestBody LessonDto.Enrollment request) {
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
    public RedirectView editLesson(@ModelAttribute LessonDto.Edit lessonDto) {
        lessonService.editLesson(lessonDto);
        return new RedirectView("/lesson/" + lessonDto.getId());
    }

}