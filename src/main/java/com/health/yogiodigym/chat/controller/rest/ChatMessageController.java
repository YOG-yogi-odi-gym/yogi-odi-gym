package com.health.yogiodigym.chat.controller.rest;

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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/unread/{roomId}")
    public ResponseEntity<?> getUnreadMessages(@AuthenticationPrincipal MemberOAuth2User loginUser,
                                               @PathVariable("roomId") String roomId) {
        log.info("읽지 않은 메시지 요청: {}", roomId);

        List<MessageResponseDto> unReadMessages = chatMessageService.getUnReadMessages(loginUser.getMember(), roomId);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, "안 읽은 메시지 조회에 성공하였습니다.", unReadMessages));
    }

    @GetMapping("/read/{roomId}")
    public ResponseEntity<?> getReadMessages(@AuthenticationPrincipal MemberOAuth2User loginUser,
                                             @PathVariable("roomId") String roomId,
                                             @PageableDefault(size = 30) Pageable pageable) {
        log.info("읽은 메시지 요청: {}", roomId);

        List<MessageResponseDto> readMessages = chatMessageService.getReadMessages(loginUser.getMember(), roomId, pageable);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, "읽은 메시지 조회에 성공하였습니다.", readMessages));
    }

    @GetMapping("/total/{roomId}")
    public ResponseEntity<?> countTotalPages(@AuthenticationPrincipal MemberOAuth2User loginUser,
                                             @PathVariable("roomId") String roomId) {
        int total = chatMessageService.getTotalPage(loginUser.getMember(), roomId);

        log.info("전체 페이지 수 요청: {}", total);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, "전체 페이지 수 로드에 성공하였습니다.", total));
    }

    @PutMapping("/{roomId}/last/{lastMessageId}")
    public ResponseEntity<?> updateLastMessage(@AuthenticationPrincipal MemberOAuth2User loginUser,
                                               @PathVariable("roomId") String roomId,
                                               @PathVariable("lastMessageId") Long lastMessageId) {

        log.info("마지막 메시지 업데이트 요청: {}", lastMessageId);

        chatMessageService.updateReadStatus(loginUser.getMember(), roomId, lastMessageId);

        return ResponseEntity.ok()
                .body(new HttpResponse(HttpStatus.OK, "마지막에 읽은 메시지를 업데이트했습니다.", null));
    }

}
