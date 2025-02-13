package com.health.yogiodigym.auth.service;

import com.health.yogiodigym.auth.entity.Member;
import com.health.yogiodigym.auth.entity.Team;
import com.health.yogiodigym.auth.repository.MemberRepository;
import com.health.yogiodigym.auth.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public void insertMember(String name){
        memberRepository.save(Member.builder().name(name).build());
    }

    public List<Member> findMember(Long teamId){
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Team team = optionalTeam.get();

        return memberRepository.findByTeam(team);
    }

    public void updateMember(String name) {
        Member member = memberRepository.findByName(name);
        member.updateMember("김도현");
        memberRepository.save(member);
    }
}
