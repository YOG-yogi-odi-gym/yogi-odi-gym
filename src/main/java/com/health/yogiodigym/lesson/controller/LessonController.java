package com.health.yogiodigym.lesson.controller;

import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.lesson.dto.LessonEditDto;
import com.health.yogiodigym.lesson.dto.LessonEnrollmentDto;
import com.health.yogiodigym.lesson.dto.LessonRequestDto;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.service.LessonEnrollmentService;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final MemberRepository memberRepository;
    private final LessonEnrollmentService lessonEnrollmentService;

    public LessonController(LessonService lessonService,
                            MemberRepository memberRepository,
                            LessonEnrollmentService lessonEnrollmentService) {
        this.lessonService = lessonService;
        this.memberRepository = memberRepository;
        this.lessonEnrollmentService = lessonEnrollmentService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchLessons(@RequestParam(required = false) String lessonKeyword,
                                                        @RequestParam(required = false) String searchColumn,
                                                        @RequestParam(required = false) Integer days,
                                                         @RequestParam(required = false) List<Long> categories,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {

        Page<LessonDto> lessons = lessonService.searchLessons(lessonKeyword, searchColumn, days, categories, page, size);
        return ResponseEntity.ok(lessons);
    }

    @PostMapping("/register")
    public RedirectView registerLesson(@ModelAttribute LessonRequestDto lessonDto) {
        Member master = memberRepository.findByEmail("test4@test.com");
        lessonService.registerLesson(lessonDto, master);

        return new RedirectView("/lesson");
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollLesson(@RequestBody LessonEnrollmentDto request) {
        boolean success = lessonEnrollmentService.enrollLesson(request.getMemberId(), request.getLessonId());
        return ResponseEntity.ok(Map.of("success", success));
    }

    @DeleteMapping("/cancel/{memberId}/{lessonId}")
    public ResponseEntity<?> cancelEnrollment(@PathVariable Long memberId, @PathVariable Long lessonId) {
        boolean success = lessonEnrollmentService.cancelEnrollment(memberId, lessonId);
        return ResponseEntity.ok(Map.of("success", success));
    }

    @GetMapping("/enrolled")
    public ResponseEntity<?> isEnrolled(@RequestParam Long lessonId, @RequestParam Long memberId) {
        return ResponseEntity.ok(lessonEnrollmentService.isUserEnrolled(memberId, lessonId));
    }

    @PostMapping("/edit")
    public RedirectView editLesson(@ModelAttribute LessonEditDto lessonDto) {
        lessonService.editLesson(lessonDto);
        return new RedirectView("/lesson/" + lessonDto.getId());
    }

}