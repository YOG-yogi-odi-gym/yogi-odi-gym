package com.health.yogiodigym.chat.controller.rest;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import com.health.yogiodigym.chat.service.ChatMessageService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/read/{roomId}")
    public ResponseEntity<?> getReadMessages(@AuthenticationPrincipal MemberOAuth2User loginUser,
                                             @PathVariable("roomId") String roomId,
                                             @RequestParam("last_message_id") Long lastMessageId,
                                             @PageableDefault(size = 30) Pageable pageable) {
        log.info("읽은 메시지 요청: {}", roomId);

        List<MessageResponseDto> readMessages = chatMessageService.getReadMessages(loginUser.getMember(), roomId, lastMessageId, pageable);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, GET_READ_MESSAGES_SUCCESS.getMessage(), readMessages));
    }

    @PutMapping("/{roomId}/last/{lastMessageId}")
    public ResponseEntity<?> updateLastMessage(@AuthenticationPrincipal MemberOAuth2User loginUser,
                                               @PathVariable("roomId") String roomId,
                                               @PathVariable("lastMessageId") String lastMessageId) {

        log.info("마지막 메시지 업데이트 요청: {}", lastMessageId);

        chatMessageService.updateReadStatus(loginUser.getMember(), roomId, Long.parseLong(lastMessageId));

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, UPDATE_LAST_READ_MESSAGE_SUCCESS.getMessage(), null));
    }

}
