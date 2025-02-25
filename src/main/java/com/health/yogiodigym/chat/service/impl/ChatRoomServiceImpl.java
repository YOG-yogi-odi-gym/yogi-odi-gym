package com.health.yogiodigym.chat.service.impl;

import static com.health.yogiodigym.chat.config.ChatConstants.ENTER_CHAT_ROOM_MESSAGE_PREFIX;
import static com.health.yogiodigym.chat.config.ChatConstants.QUIT_CHAT_ROOM_MESSAGE_SUFFIX;

import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.dto.MessageDto;
import com.health.yogiodigym.chat.entity.ChatParticipant;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatMessageRepository;
import com.health.yogiodigym.chat.repository.ChatParticipantRepository;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.chat.service.ChatRoomService;
import com.health.yogiodigym.chat.service.KafkaProducerService;
import com.health.yogiodigym.common.exception.AlreadyChatParticipantException;
import com.health.yogiodigym.common.exception.ChatRoomNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotInChatRoomException;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private static final int CHAT_ROOM_ID_LENGTH = 10;

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final LessonEnrollmentRepository lessonEnrollmentRepository;
    private final KafkaProducerService kafkaProducerService;

    // TODO 로그인 구현 완료 후 UserDetails에서 User객체 사용

    @Override
    public void enterChatRoom(Long memberId, String roomId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        validateChatParticipant(member, chatRoom);

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();
        chatParticipantRepository.save(chatParticipant);

        sendChatMessage(member, roomId, member.getName() + ENTER_CHAT_ROOM_MESSAGE_PREFIX);
    }

    private void validateChatParticipant(Member member, ChatRoom chatRoom) {
        if (chatParticipantRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new AlreadyChatParticipantException();
        }
    }

    @Override
    public void createChatRoom(Member member, boolean isGroupChat) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(createChatRoomId())
                .isGroupChat(isGroupChat)
                .build();
        chatRoomRepository.save(chatRoom);

        ChatParticipant chatParticipant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();
        chatParticipantRepository.save(chatParticipant);
    }

    private String createChatRoomId() {
        return UUID.randomUUID().toString().substring(0, CHAT_ROOM_ID_LENGTH);
    }

    @Override
    public void deleteChatRoom(ChatRoom chatRoom) {
        chatMessageRepository.deleteAllByChatRoomInBatch(chatRoom);
        chatParticipantRepository.deleteByChatRoom(chatRoom);
        chatRoomRepository.delete(chatRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getChatRooms(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        return lessonEnrollmentRepository.findAllByMember(member)
                .stream()
                .map(LessonEnrollment::getLesson)
                .map(ChatRoomResponseDto::new)
                .toList();
    }

    @Override
    public void kickMember(Long instructorId, Long memberId, String chatRoomId) {
        Member instructor = memberRepository.findById(instructorId)
                .orElseThrow(() -> new MemberNotFoundException(instructorId));
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(chatRoomId));
        chatParticipantRepository.findByMemberAndChatRoom(instructor, chatRoom)
                .orElseThrow(() -> new MemberNotInChatRoomException(instructorId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        ChatParticipant memberParticipant = chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom)
                .orElseThrow(() -> new MemberNotInChatRoomException(memberId));
        chatParticipantRepository.delete(memberParticipant);
    }

    @Override
    public void quitChatRoom(Long memberId, String roomId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));

        ChatParticipant chatParticipant = chatParticipantRepository.findByMemberAndChatRoom(member, chatRoom)
                .orElseThrow(() -> new MemberNotInChatRoomException(memberId));
        chatParticipantRepository.delete(chatParticipant);

        sendChatMessage(member, roomId, member.getName() + QUIT_CHAT_ROOM_MESSAGE_SUFFIX);
    }

    private void sendChatMessage(Member member, String roomId, String content) {
        MessageDto message = MessageDto.builder()
                .senderId(member.getId())
                .senderName(member.getName())
                .roomId(roomId)
                .message(content)
                .build();
        kafkaProducerService.sendMessage(message);
    }

}
