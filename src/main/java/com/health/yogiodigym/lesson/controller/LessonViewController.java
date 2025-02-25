package com.health.yogiodigym.lesson.controller;

import com.health.yogiodigym.lesson.dto.LessonDetailDto;
import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.lesson.dto.MemberLatLonDto;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lesson")
public class LessonViewController {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final LessonService lessonService;

    public LessonViewController(CategoryRepository categoryRepository,
                                MemberRepository memberRepository,
                                LessonService lessonService) {
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
        this.lessonService = lessonService;
    }

    @GetMapping
    public String showLesson(Model model) {
        Member member = memberRepository.findByEmail("test4@test.com");

        model.addAttribute("member", new MemberLatLonDto(member));
        model.addAttribute("categories", categoryRepository.findByCode("1"));

        return "lesson/lesson";
    }

    @GetMapping("/register")
    public String showLessonRegister(Model model) {

        model.addAttribute("member",memberRepository.findByEmail("test4@test.com"));
        model.addAttribute("categories", categoryRepository.findByCode("1"));

        return "lesson/register";
    }

    @GetMapping("/{id}")
    public String showLessonDetail(@PathVariable Long id, Model model) {
        Member member = memberRepository.findByEmail("test4@test.com");
        LessonDetailDto lessonDetailDto = lessonService.findLessonById(id);

        model.addAttribute("member", new MemberLatLonDto(member));
        model.addAttribute("lesson", lessonDetailDto);
        return "lesson/detail";
    }

    @GetMapping("/{lessonId}/edit")
    public String showLessonUpdate(@PathVariable Long lessonId, Model model) {
        LessonDetailDto lessonDetailDto = lessonService.findLessonById(lessonId);

        model.addAttribute("daysSelected", daysSelected(lessonDetailDto.getDays()));
        model.addAttribute("categories", categoryRepository.findByCode("1"));
        model.addAttribute("lesson", lessonDetailDto);
        return "lesson/edit";
    }

    public boolean[] daysSelected(int days) {
        boolean[] daysSelected = new boolean[7];
        for (int i = 0; i < 7; i++) {
            daysSelected[i] = (days & (1 << i)) != 0;
        }
        return daysSelected;
    }
}