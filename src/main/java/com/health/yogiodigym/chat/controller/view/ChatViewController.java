package com.health.yogiodigym.chat.controller.view;

import com.health.yogiodigym.chat.service.ChatMessageService;
import com.health.yogiodigym.chat.service.ChatParticipantService;
import com.health.yogiodigym.chat.service.ChatRoomService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatViewController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatParticipantService chatParticipantService;

    @GetMapping("/chat")
    public String chatRoom(@AuthenticationPrincipal MemberOAuth2User loginUser,
                           @RequestParam String roomId,
                           Model model) {

        log.info("채팅페이지 입장, roomId: {}", roomId);

        Member member = loginUser.getMember();
        Long lastMessageId = chatMessageService.getLastMessageId(member, roomId);
        model.addAttribute("lastMessageId", lastMessageId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("totalPage", chatMessageService.getTotalPage(roomId, lastMessageId));
        model.addAttribute("chatParticipants", chatParticipantService.getChatParticipants(member, roomId));
        model.addAttribute("chatRooms", chatRoomService.getChatRooms(member));
        model.addAttribute("chatRoomDetail", chatRoomService.getChatRoomDetail(roomId));
        model.addAttribute("readMessages", chatMessageService.getReadMessages(member, roomId, lastMessageId, PageRequest.of(0, 30)));
        model.addAttribute("unReadMessages", chatMessageService.getUnReadMessages(member, roomId));

        return "chat/chat";
    }

}
