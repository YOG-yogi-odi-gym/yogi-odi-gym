package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.AdminService;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberResponseDto> searchMembers(String keyword) {
        List<Member> members = memberRepository.findMembers(keyword);
        return members.stream().map(MemberResponseDto::from).toList();
    }

}
