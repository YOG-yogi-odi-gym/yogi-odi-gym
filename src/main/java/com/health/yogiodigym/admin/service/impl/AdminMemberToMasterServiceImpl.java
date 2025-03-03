package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.MemberToMasterDto.*;
import com.health.yogiodigym.admin.service.service.AdminMemberToMasterService;
import com.health.yogiodigym.member.entity.Authority;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberToMaster;
import com.health.yogiodigym.member.repository.AuthorityRepository;
import com.health.yogiodigym.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberToMasterResponseDto> findAllMemberToMasters() {

        return memberToMasterRepository.findAll()
                .stream()
                .map(MemberToMasterResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setMasterStatus(Long applyMemberId) {

        MemberToMaster memberToMaster = memberToMasterRepository.findByMemberId(applyMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청 멤버가 존재하지 않습니다."));

        Long memberId = memberToMaster.getMember().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        boolean exist = memberToMasterRepository.existsById(memberId);
        if (exist) {
            throw new IllegalArgumentException("해당 멤버는 이미 강사 권한을 가지고 있습니다.");
        }

        Authority authority = Authority.builder()
                .member(member)
                .role("ROLE_MASTER")
                .build();

        authorityRepository.save(authority);
        memberToMasterRepository.delete(memberToMaster);
    }
}

