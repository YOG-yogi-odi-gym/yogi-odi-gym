package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.MemberToMasterDto.*;
import com.health.yogiodigym.admin.service.service.AdminMemberToMasterService;
import com.health.yogiodigym.member.repository.MemberToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminMemberToMasterServiceImpl implements AdminMemberToMasterService {

    private final MemberToMasterRepository memberToMasterRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberToMasterResponseDto> findAllMemberToMasters() {

        return memberToMasterRepository.findAll()
                .stream()
                .map(MemberToMasterResponseDto::from)
                .collect(Collectors.toList());
    }
}

