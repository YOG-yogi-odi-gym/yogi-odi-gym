package com.health.yogiodigym.calendar.service;

//import com.health.yogiodigym.calendar.entity.Member;
//import com.health.yogiodigym.calendar.repository.MemberRepository;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public boolean login(String email, String password) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return member.getPwd().equals(password);
        }
        return false;
    }

    public Member getMemberByEmail(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);
        return member.orElse(null);
    }


}
