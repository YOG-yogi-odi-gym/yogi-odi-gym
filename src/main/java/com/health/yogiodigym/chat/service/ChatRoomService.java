package com.health.yogiodigym.chat.service;

import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;

public interface ChatRoomService {
    void enterChatRoom(Long memberId, String roomId);

    void createChatRoom(Member member, boolean isGroupChat);

    void deleteChatRoom(ChatRoom chatRoom);

    List<ChatRoomResponseDto> getChatRooms(Long memberId);

    void kickMember(Long managerId, Long memberId, String chatRoomId);

    void quitChatRoom(Long memberId, String chatRoomId);
}
