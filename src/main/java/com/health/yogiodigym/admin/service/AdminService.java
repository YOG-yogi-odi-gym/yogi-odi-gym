package com.health.yogiodigym.admin.service;


import com.health.yogiodigym.admin.dto.MemberDto.MemberResponseDto;

import java.util.List;

public interface AdminService {
    List<MemberResponseDto> searchMembers(String keyword);
}
