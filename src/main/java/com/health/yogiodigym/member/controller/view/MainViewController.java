package com.health.yogiodigym.member.controller.view;

import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.service.GraphAverageService;
import com.health.yogiodigym.board.dto.BoardDto.BoardDetailDto;
import com.health.yogiodigym.board.service.BoardService;
import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainViewController {

    private final GraphAverageService graphAverageService;
    private final ChatRoomService chatRoomService;
    private final BoardService boardService;

    @GetMapping("/")
    public String main(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/member/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal MemberOAuth2User loginUser, Model model) {

        model.addAttribute("calorieAverages", graphAverageService.getCalorieAverage());
        model.addAttribute("exerciseTimeAverages", graphAverageService.getExerciseTimeAverage());
        model.addAttribute("myCalories", graphAverageService.getMyPreviousDateCalorie(loginUser.getMember()));
        model.addAttribute("myExerciseTimeAverages", graphAverageService.getMyPreviousDateExerciseTime(loginUser.getMember()));

        List<ChatRoomResponseDto> chatRooms = chatRoomService.getChatRooms(loginUser.getMember());
        model.addAttribute("chatRooms", chatRooms);
        log.info("채팅방 목록: {}", chatRooms);

        List<BoardDetailDto> boards = boardService.getBoardsTop10();
        model.addAttribute("popularBoards", boards);
        log.info("실시간 인기 게시글: {}", boards);

        return "dashboard";
    }
}
