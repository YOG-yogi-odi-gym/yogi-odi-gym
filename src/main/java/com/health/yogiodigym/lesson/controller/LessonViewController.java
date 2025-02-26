package com.health.yogiodigym.lesson.controller;

import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.lesson.dto.MemberLatLonDto;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonViewController {

    private final MemberRepository memberRepository;
    private final LessonService lessonService;

    @GetMapping
    public String showLesson(Model model) {
        Member member = memberRepository.findByEmail("chulsoo@naver.com");

        model.addAttribute("member", new MemberLatLonDto(member));
        model.addAttribute("categories", lessonService.getCategoriesByCode("lesson"));

        return "lesson/lesson";
    }

    @GetMapping("/register")
    public String showLessonRegister(Model model) {

        model.addAttribute("member", memberRepository.findByEmail("chulsoo@naver.com"));
        model.addAttribute("categories", lessonService.getCategoriesByCode("lesson"));

        return "lesson/register";
    }

    @GetMapping("/{id}")
    public String showLessonDetail(@PathVariable Long id, Model model) {
        Member member = memberRepository.findByEmail("chulsoo@naver.com");
        LessonDto.Detail lessonDetailDto = lessonService.findLessonById(id);

        model.addAttribute("member", new MemberLatLonDto(member));
        model.addAttribute("lesson", lessonDetailDto);
        return "lesson/detail";
    }

    @GetMapping("/{lessonId}/edit")
    public String showLessonUpdate(@PathVariable Long lessonId, Model model) {
        LessonDto.Detail lessonDetailDto = lessonService.findLessonById(lessonId);

        model.addAttribute("daysSelected", lessonService.daysSelected(lessonDetailDto.getDays()));
        model.addAttribute("categories", lessonService.getCategoriesByCode("lesson"));
        model.addAttribute("lesson", lessonDetailDto);
        return "lesson/edit";
    }


}