package com.health.yogiodigym.chat.service;

import static com.health.yogiodigym.chat.config.ChatConstants.QUIT_CHAT_ROOM_MESSAGE_SUFFIX;
import static com.health.yogiodigym.common.message.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.health.yogiodigym.chat.dto.MessageDto.MessageRequestDto;
import com.health.yogiodigym.chat.dto.MessageDto.MessageResponseDto;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatMessageRepository;
import com.health.yogiodigym.chat.repository.ChatParticipantRepository;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.chat.service.impl.ChatRoomServiceImpl;
import com.health.yogiodigym.common.exception.AlreadyChatParticipantException;
import com.health.yogiodigym.common.exception.ChatRoomNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotInChatRoomException;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ChatParticipantRepository chatParticipantRepository;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private LessonEnrollmentRepository lessonEnrollmentRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;

    private Long instructorId;
    private Long memberId;
    private Long chatRoomId;
    private String roomId;

    @BeforeEach
    public void setUp() {
        this.instructorId = 1L;
        this.memberId = 2L;
        this.chatRoomId = 1L;
        this.roomId = "test-room";
    }

    @Nested
    @DisplayName("채팅방 입장 테스트")
    class EnterChatRoomTest {

        @Test
        @DisplayName("채팅방 입장 성공")
        void testEnterChatRoom() {
            // given
            ChatRoom mockChatRoom = ChatRoom.builder().id(chatRoomId).build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));
            when(chatParticipantRepository.existsByMemberAndChatRoom(any(Member.class), any(ChatRoom.class)))
                    .thenReturn(false);

            // when
            chatRoomService.enterChatRoom(mock(Member.class), roomId);

            // then
            verify(chatParticipantRepository, times(1)).save(any(ChatParticipant.class));
            verify(kafkaProducerService, times(1)).sendMessage(any(MessageResponseDto.class));
        }

        @Test
        @DisplayName("채팅방이 존재하지 않을 경우 예외 발생")
        void testChatRoomNotFoundWhenEnterChatRoom() {
            // given
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> chatRoomService.enterChatRoom(mock(Member.class), roomId))
                    .isInstanceOf(ChatRoomNotFoundException.class)
                    .hasMessage(CHAT_ROOM_NOT_FOUND.getMessage() + "-> " + roomId);
        }

        @Test
        @DisplayName("채팅참여여부가 이미 존재할 경우 예외 발생")
        void testChatParticipantExistsWhenEnterChatRoom() {
            // given
            ChatRoom mockChatRoom = ChatRoom.builder().id(chatRoomId).build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));
            when(chatParticipantRepository.existsByMemberAndChatRoom(any(Member.class), any(ChatRoom.class)))
                    .thenReturn(true);

            // when
            // then
            assertThatThrownBy(() -> chatRoomService.enterChatRoom(mock(Member.class), roomId))
                    .isInstanceOf(AlreadyChatParticipantException.class)
                    .hasMessage(ALREADY_CHAT_PARTICIPANT.getMessage());
        }

    }

    @Nested
    @DisplayName("채팅방 생성 테스트")
    class CreateChatRoomTest {

        @Test
        @DisplayName("채팅방 생성 성공")
        void testCreateChatRoom() {
            // given
            Member instructor = Member.builder()
                    .id(instructorId)
                    .build();

            // when
            chatRoomService.createChatRoom(instructor, true);

            // then
            ArgumentCaptor<ChatRoom> chatRoomArgumentCaptor = ArgumentCaptor.forClass(ChatRoom.class);
            verify(chatRoomRepository).save(chatRoomArgumentCaptor.capture());
            ChatRoom savedChatRoom = chatRoomArgumentCaptor.getValue();

            ArgumentCaptor<ChatParticipant> chatParticipantArgumentCaptor = ArgumentCaptor.forClass(ChatParticipant.class);
            verify(chatParticipantRepository).save(chatParticipantArgumentCaptor.capture());
            ChatParticipant chatParticipant = chatParticipantArgumentCaptor.getValue();

            assertThat(savedChatRoom.getRoomId().length()).isEqualTo(10);
            assertThat(chatParticipant.getChatRoom()).isEqualTo(savedChatRoom);
            assertThat(chatParticipant.getMember()).isEqualTo(instructor);
        }

    }

    @Nested
    @DisplayName("채팅방 폐쇄 테스트")
    class DeleteChatRoomTest {

        @Test
        @DisplayName("채팅방 폐쇄 성공")
        void testDeleteChatRoom() {
            // given
            ChatRoom mockChatRoom = ChatRoom.builder()
                    .id(chatRoomId)
                    .roomId(roomId)
                    .isGroupChat(true)
                    .build();

            // when
            chatRoomService.deleteChatRoom(mockChatRoom);

            // then
            verify(chatMessageRepository, times(1)).deleteAllByChatRoomInBatch(mockChatRoom);
            verify(chatParticipantRepository, times(1)).deleteByChatRoom(mockChatRoom);
            verify(chatRoomRepository, times(1)).delete(mockChatRoom);
        }

    }

    @Nested
    @DisplayName("채팅방 목록 조회 테스트")
    class GetChatRoomsTest {

        @Test
        @DisplayName("채팅방 목록 조회 성공")
        void testGetChatRooms() {
            // given
            Member mockMember = Member.builder()
                    .id(memberId)
                    .build();

            ChatRoom chatRoom1 = ChatRoom.builder()
                    .id(1L)
                    .roomId("test-room-1")
                    .isGroupChat(true)
                    .build();
            ChatRoom chatRoom2 = ChatRoom.builder()
                    .id(2L)
                    .roomId("test-room-2")
                    .isGroupChat(true)
                    .build();
            ChatRoom chatRoom3 = ChatRoom.builder()
                    .id(3L)
                    .roomId("test-room-3")
                    .isGroupChat(true)
                    .build();

            List<LessonEnrollment> enrolls = List.of(
                    LessonEnrollment.builder()
                            .id(1L)
                            .member(mockMember)
                            .lesson(Lesson.builder().title("lesson1").chatRoom(chatRoom1).build())
                            .build(),
                    LessonEnrollment.builder()
                            .id(2L)
                            .member(mockMember)
                            .lesson(Lesson.builder().title("lesson2").chatRoom(chatRoom2).build())
                            .build(),
                    LessonEnrollment.builder()
                            .id(3L)
                            .member(mockMember)
                            .lesson(Lesson.builder().title("lesson3").chatRoom(chatRoom3).build())
                            .build()
            );
            when(lessonEnrollmentRepository.findAllByMember(any(Member.class))).thenReturn(enrolls);

            ChatParticipant mockChatParticipant = ChatParticipant.builder()
                    .member(mockMember)
                    .chatRoom(chatRoom1)
                    .lastReadMessageId(1L)
                    .build();
            when(chatParticipantRepository.findByMemberAndChatRoom(any(Member.class), any(ChatRoom.class)))
                    .thenReturn(Optional.of(mockChatParticipant));

            // when
            List<ChatRoomResponseDto> chatRoomsResponse = chatRoomService.getChatRooms(mock(Member.class));

            // then
            assertThat(chatRoomsResponse.size()).isEqualTo(3);
            assertThat(chatRoomsResponse.get(0).getLessonTitle()).isEqualTo("lesson1");
            assertThat(chatRoomsResponse.get(0).getRoomId()).isEqualTo("test-room-1");
            assertThat(chatRoomsResponse.get(0).isGroupChat()).isEqualTo(true);
            assertThat(chatRoomsResponse.get(1).getLessonTitle()).isEqualTo("lesson2");
            assertThat(chatRoomsResponse.get(1).getRoomId()).isEqualTo("test-room-2");
            assertThat(chatRoomsResponse.get(1).isGroupChat()).isEqualTo(true);
        }

    }

    @Nested
    @DisplayName("채팅방 회원 강퇴 테스트")
    class KickMemberTest {

        @Test
        @DisplayName("채팅방 회원 강퇴 성공")
        void testKickMember() {
            // given
            Member mockInstructor = Member.builder().id(instructorId).build();
            when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mockInstructor));

            ChatRoom mockChatRoom = ChatRoom.builder()
                    .id(chatRoomId)
                    .roomId(roomId)
                    .isGroupChat(true)
                    .build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));

            ChatParticipant chatParticipant = ChatParticipant.builder()
                    .member(mockInstructor)
                    .chatRoom(mockChatRoom)
                    .build();
            when(chatParticipantRepository.findByMemberAndChatRoom(any(), any())).thenReturn(Optional.of(chatParticipant));

            // when
            chatRoomService.kickMember(mockInstructor, memberId, roomId);

            // then
            verify(chatParticipantRepository, times(1)).delete(chatParticipant);
        }

        @Test
        @DisplayName("회원 강퇴시 회원이 채팅방 구성원이 아닐 경우 예외 발생")
        void testInstructorNotInChatRoomWhenKickMember() {
            // given
            Member instructor = Member.builder().id(instructorId).build();

            ChatRoom mockChatRoom = ChatRoom.builder()
                    .id(chatRoomId)
                    .roomId(roomId)
                    .isGroupChat(true)
                    .build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));
            when(chatParticipantRepository.findByMemberAndChatRoom(any(), any())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> chatRoomService.kickMember(instructor, memberId, roomId))
                    .isInstanceOf(MemberNotInChatRoomException.class)
                    .hasMessage(MEMBER_NOT_IN_CHAT_ROOM.getMessage() + "-> " + instructorId);
        }

        @Test
        @DisplayName("회원 강퇴시 채팅방이 존재하지 않을 경우 예외 발생")
        void testChatRoomNotFoundWhenKickMember() {
            // given
            Member mockMember = Member.builder().id(memberId).build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> chatRoomService.kickMember(mockMember, memberId, roomId))
                    .isInstanceOf(ChatRoomNotFoundException.class)
                    .hasMessage(CHAT_ROOM_NOT_FOUND.getMessage() + "-> " + roomId);
        }
    }

    @Nested
    @DisplayName("채팅방 탈퇴 테스트")
    class QuitChatRoomTest {

        @Test
        @DisplayName("채팅방 탈퇴 성공")
        void testQuitChatRoom() {
            // given
            Member mockMember = Member.builder()
                    .id(memberId)
                    .name("test member")
                    .build();

            ChatRoom mockChatRoom = ChatRoom.builder().id(chatRoomId).build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));

            ChatParticipant mockChatParticipant = ChatParticipant.builder()
                    .member(mockMember)
                    .chatRoom(mockChatRoom)
                    .build();
            when(chatParticipantRepository.findByMemberAndChatRoom(any(), any())).thenReturn(Optional.of(mockChatParticipant));

            // when
            chatRoomService.quitChatRoom(mockMember, roomId);

            // then
            verify(chatParticipantRepository, times(1)).delete(mockChatParticipant);

            ArgumentCaptor<MessageResponseDto> messageArgumentCaptor = ArgumentCaptor.forClass(MessageResponseDto.class);
            verify(kafkaProducerService, times(1)).sendMessage(messageArgumentCaptor.capture());
            MessageResponseDto message = messageArgumentCaptor.getValue();
            assertThat(message.getSenderId()).isEqualTo(mockMember.getId());
            assertThat(message.getRoomId()).isEqualTo(roomId);
            assertThat(message.getMessage()).isEqualTo(mockMember.getName() + QUIT_CHAT_ROOM_MESSAGE_SUFFIX);
        }

        @Test
        @DisplayName("채팅방 탈퇴시 채팅방이 존재하지 않을 경우 예외 발생")
        void testChatRoomNotFoundWhenQuitChatRoom() {
            // given
            Member mockMember = Member.builder().id(memberId).build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> chatRoomService.quitChatRoom(mockMember, roomId))
                    .isInstanceOf(ChatRoomNotFoundException.class)
                    .hasMessage(CHAT_ROOM_NOT_FOUND.getMessage() + "-> " + roomId);
        }

        @Test
        @DisplayName("회원이 채팅방 구성원이 아닐 경우 예외 발생")
        void testMemberNotInChatRoomWhenQuitChatRoom() {
            // given
            Member mockMember = Member.builder().id(memberId).build();

            ChatRoom mockChatRoom = ChatRoom.builder()
                    .id(chatRoomId)
                    .roomId(roomId)
                    .isGroupChat(true)
                    .build();
            when(chatRoomRepository.findByRoomId(anyString())).thenReturn(Optional.of(mockChatRoom));

            when(chatParticipantRepository.findByMemberAndChatRoom(any(), any())).thenReturn(Optional.empty());

            // when
            // then
            assertThatThrownBy(() -> chatRoomService.quitChatRoom(mockMember, roomId))
                    .isInstanceOf(MemberNotInChatRoomException.class)
                    .hasMessage(MEMBER_NOT_IN_CHAT_ROOM.getMessage() + "-> " + memberId);
        }
    }

}
