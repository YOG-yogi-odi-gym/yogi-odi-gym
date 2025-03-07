package com.health.yogiodigym.admin.service.service;

import com.health.yogiodigym.admin.dto.MemberToMasterDto.*;

import java.util.List;

public interface AdminMemberToMasterService {


    List<MemberToMasterResponseDto> findAllByOrderByEnrollDateAsc();

    void setMasterStatus(Long applyMemberId);

    void rejectMasterStatus(Long applyId);
}
