package com.health.yogiodigym.chat.controller.rest;

import static com.health.yogiodigym.common.message.ErrorMessage.*;
import static com.health.yogiodigym.common.message.SuccessMessage.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.service.ChatRoomService;
import com.health.yogiodigym.common.exception.ChatRoomNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotInChatRoomException;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.util.TestSecurityConfig;
import com.health.yogiodigym.util.WithCustomMockUser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WithCustomMockUser
@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = ChatRoomController.class)
class ChatRoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ChatRoomService chatRoomService;

    Long instructorId;
    Long memberId;
    String roomId;

    @BeforeEach
    void setUp() {
        this.instructorId = 1L;
        this.memberId = 2L;
        this.roomId = "test-room";
    }

    @Nested
    @DisplayName("내 채팅방 목록 조회 테스트")
    class GetMyChatRoomsTest {

        @Test
        @DisplayName("내 채팅방 목록 조회 성공")
        void testGetMyChatRooms() throws Exception {
            // given
            List<ChatRoomResponseDto> chatRooms = List.of(
                    ChatRoomResponseDto.builder().build(),
                    ChatRoomResponseDto.builder().build(),
                    ChatRoomResponseDto.builder().build()
            );
            when(chatRoomService.getChatRooms(any(Member.class))).thenReturn(chatRooms);

            // when
            // then
            mvc.perform(get("/api/chat-rooms")
                            .param("memberId", String.valueOf(memberId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(200)))
                    .andExpect(jsonPath("$.message", equalTo(GET_MY_CHAT_ROOMS_SUCCESS.getMessage())))
                    .andExpect(jsonPath("$.data.size()", equalTo(3)));
        }

        @Test
        @DisplayName("회원이 존재하지 않을 경우 예외 메시지 전송")
        void testMemberNotFoundWhenGetMyChatRooms() throws Exception {
            // given
            doThrow(new MemberNotFoundException(memberId)).when(chatRoomService).getChatRooms(any(Member.class));

            // when
            // then
            mvc.perform(get("/api/chat-rooms")
                            .param("memberId", String.valueOf(memberId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(MEMBER_NOT_FOUND.getMessage() + "-> " + memberId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

    }

    @Nested
    @DisplayName("회원 강퇴 테스트")
    class KickMemberTest {

        @Test
        @DisplayName("회원 강퇴 성공")
        void testKickMember() throws Exception {
            // given
            doNothing().when(chatRoomService).kickMember(any(Member.class), anyLong(), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}/members/{memberId}", roomId, memberId)
                            .param("instructorId", String.valueOf(instructorId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(200)))
                    .andExpect(jsonPath("$.message", equalTo(KICK_MEMBER_SUCCESS.getMessage())))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("회원이 존재하지 않을 경우 예외 메시지 전송")
        void testMemberNotFoundWhenKickMember() throws Exception {
            // given
            doThrow(new MemberNotFoundException(memberId)).when(chatRoomService).kickMember(any(Member.class), anyLong(), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}/members/{memberId}", roomId, memberId)
                            .param("instructorId", String.valueOf(instructorId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(MEMBER_NOT_FOUND.getMessage() + "-> " + memberId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("채팅방이 존재하지 않을 경우 예외 메시지 전송")
        void testChatRoomNotFoundWhenKickMember() throws Exception {
            // given
            doThrow(new ChatRoomNotFoundException(roomId)).when(chatRoomService).kickMember(any(Member.class), anyLong(), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}/members/{memberId}", roomId, memberId)
                            .param("instructorId", String.valueOf(instructorId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(CHAT_ROOM_NOT_FOUND.getMessage() + "-> " + roomId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("채팅 참여여부가 존재하지 않을 경우 예외 메시지 전송")
        void testChatParticipantNotFoundWhenKickMember() throws Exception {
            // given
            doThrow(new MemberNotInChatRoomException(memberId)).when(chatRoomService).kickMember(any(Member.class), anyLong(), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}/members/{memberId}", roomId, memberId)
                            .param("instructorId", String.valueOf(instructorId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(MEMBER_NOT_IN_CHAT_ROOM.getMessage() + "-> " + memberId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }
    }

    @Nested
    @DisplayName("채팅방 나가기 테스트")
    class QuitChatRoomTest {

        @Test
        @DisplayName("채팅방 나가기 성공")
        void testQuitChatRoom() throws Exception {
            // given
            doNothing().when(chatRoomService).quitChatRoom(any(Member.class), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}", roomId)
                            .param("memberId", String.valueOf(memberId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status", equalTo(200)))
                    .andExpect(jsonPath("$.message", equalTo(QUIT_CHAT_ROOM_SUCCESS.getMessage())))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("회원이 존재하지 않을 경우 예외 메시지 전송")
        void testMemberNotFoundWhenQuitChatRoom() throws Exception {
            // given
            doThrow(new MemberNotFoundException(memberId)).when(chatRoomService).quitChatRoom(any(Member.class), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}", roomId)
                            .param("memberId", String.valueOf(memberId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(MEMBER_NOT_FOUND.getMessage() + "-> " + memberId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("채팅방이 존재하지 않을 경우 예외 메시지 전송")
        void testChatRoomNotFoundWhenQuitChatRoom() throws Exception {
            // given
            doThrow(new ChatRoomNotFoundException(roomId)).when(chatRoomService).quitChatRoom(any(Member.class), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}", roomId)
                            .param("memberId", String.valueOf(memberId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(CHAT_ROOM_NOT_FOUND.getMessage() + "-> " + roomId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }

        @Test
        @DisplayName("채팅참여여부가 존재하지 않을 경우 예외 메시지 전송")
        void testChatParticipantNotFoundWhenQuitChatRoom() throws Exception {
            // given
            doThrow(new MemberNotInChatRoomException(memberId)).when(chatRoomService).quitChatRoom(any(Member.class), anyString());

            // when
            // then
            mvc.perform(delete("/api/chat-rooms/{roomId}", roomId)
                            .param("memberId", String.valueOf(memberId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", equalTo(400)))
                    .andExpect(jsonPath("$.message", equalTo(MEMBER_NOT_IN_CHAT_ROOM.getMessage() + "-> " + memberId)))
                    .andExpect(jsonPath("$.data", equalTo(null)));
        }
    }

}