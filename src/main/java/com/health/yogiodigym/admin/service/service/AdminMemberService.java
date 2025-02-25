package com.health.yogiodigym.admin.service.service;


import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;
import com.health.yogiodigym.member.entity.Member;

import java.util.List;

public interface AdminMemberService {


    List<Member> getAllMembers();

    List<MemberResponseDto> searchMembers(String keyword);

    void setInactiveStatus(List<Long> memberIds);

//    void deleteInactiveStatus();
}
