package com.health.yogiodigym.admin.service;


import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;
import com.health.yogiodigym.member.auth.MemberStatus;

import java.util.List;

public interface AdminService {
    List<MemberResponseDto> searchMembers(String keyword);

    void deleteMembers(List<Long> memberIds, MemberStatus status);
}
