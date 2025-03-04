package com.health.yogiodigym.admin.service.service;


import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;
import java.util.List;

public interface AdminMemberService {

    List<MemberResponseDto> getAllMembers();

    List<MemberResponseDto> searchMembers(String memberKeyword);

    void setInactiveStatus(List<Long> memberIds);

//    void deleteInactiveStatus();
}
