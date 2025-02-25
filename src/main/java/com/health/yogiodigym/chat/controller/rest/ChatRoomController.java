package com.health.yogiodigym.chat.controller.rest;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.service.ChatRoomService;
import com.health.yogiodigym.common.response.HttpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<?> getMyChatRooms(@RequestParam Long memberId) {
        List<ChatRoomResponseDto> chatRooms = chatRoomService.getChatRooms(memberId);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, GET_MY_CHAT_ROOMS_SUCCESS.getMessage(), chatRooms));
    }

    @DeleteMapping("/{roomId}/members/{memberId}")
    public ResponseEntity<?> kickMember(@RequestParam Long instructorId,
                                        @PathVariable("roomId") String roomId,
                                        @PathVariable("memberId") Long memberId) {
        chatRoomService.kickMember(instructorId, memberId, roomId);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, KICK_MEMBER_SUCCESS.getMessage(), null));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> quitChatRoom(@RequestParam Long memberId, @PathVariable("roomId") String roomId) {
        chatRoomService.quitChatRoom(memberId, roomId);
        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, QUIT_CHAT_ROOM_SUCCESS.getMessage(), null));
    }

}
