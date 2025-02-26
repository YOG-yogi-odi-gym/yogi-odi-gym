package com.health.yogiodigym.admin.service;


import com.health.yogiodigym.admin.dto.MemberDto.*;

import java.util.List;

public interface AdminService {


    List<MemberResponseDto> getAllMembers();

    List<MemberResponseDto> searchMembers(String keyword);

    void setInactiveStatus(List<Long> memberIds);

//  void deleteInactiveStatus();
}
