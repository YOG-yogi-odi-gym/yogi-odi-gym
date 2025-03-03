package com.health.yogiodigym.my.controller.view;

import com.health.yogiodigym.board.dto.BoardDto;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.MemberLatLonDto;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.SEARCH_GYMS_SUCCESS;

@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyViewController {

    private final LessonService lessonService;

    @GetMapping("/board")
    public String showMyBoard(Model model) {
        model.addAttribute("categories", lessonService.getCategoriesByCode("board"));
        return "my/board";
    }

    @GetMapping("/lesson")
    public String showMyLesson(Model model,
                             @AuthenticationPrincipal MemberOAuth2User loginUser) {
        Member loginMember = loginUser.getMember();

        model.addAttribute("member", new MemberLatLonDto(loginMember));
        model.addAttribute("categories", lessonService.getCategoriesByCode("lesson"));

        return "my/lesson";
    }

}
