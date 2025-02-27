package com.health.yogiodigym.chat.service;

import com.health.yogiodigym.chat.dto.ChatRoomDto.ChatRoomResponseDto;
import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.member.entity.Member;
import java.util.List;

public interface ChatRoomService {
    void enterChatRoom(Member member, String roomId);

    ChatRoom createChatRoom(Member member, boolean isGroupChat);

    void deleteChatRoom(ChatRoom chatRoom);

    List<ChatRoomResponseDto> getChatRooms(Member member);

    void kickMember(Member instructor, Long memberId, String chatRoomId);

    void quitChatRoom(Member member, String chatRoomId);
}
