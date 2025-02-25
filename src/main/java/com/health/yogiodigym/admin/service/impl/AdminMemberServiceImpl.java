package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.MemberDto.*;
import com.health.yogiodigym.admin.service.service.AdminMemberService;
import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }

    @Override
    public List<MemberResponseDto> searchMembers(String keyword) {
        List<Member> members = memberRepository.findMembers(keyword);

        return members.stream().map(MemberResponseDto::from).toList();
    }

    @Override
    @Transactional
    public void setInactiveStatus(List<Long> memberIds) {
        LocalDate dropDate = LocalDate.now();
        memberRepository.setInactiveStatus(memberIds, MemberStatus.INACTIVE, dropDate);
    }

//    @Scheduled(cron = "00 00 00 * * *")
//    @Override
//    public void deleteInactiveStatus() {
//        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
//        List<Member> membersToDelete = memberRepository.findByStatusAndDropDateBefore(MemberStatus.INACTIVE, threeDaysAgo);
//
//        if(!membersToDelete.isEmpty()) {
//            memberRepository.deleteAll(membersToDelete);
//            System.out.println("삭제된 회원 수 : "+ membersToDelete.size());
//        }
//    }
}
