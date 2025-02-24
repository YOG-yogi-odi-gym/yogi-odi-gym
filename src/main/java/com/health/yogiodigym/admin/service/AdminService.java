package com.health.yogiodigym.admin.service;


import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;
import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.entity.Member;

import java.util.List;

public interface AdminService {


    List<Member> getAllMembers();

    List<MemberResponseDto> searchMembers(String keyword);

    void setInactiveStatus(List<Long> memberIds);
}
