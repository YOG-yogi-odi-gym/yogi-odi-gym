package com.health.yogiodigym.member.service;

import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public MemberResponseDto memberDetail(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        return null;
    }

    public List<Member> findMembers(String keyword) {
        return memberRepository.findMembers(keyword);
    }

}
