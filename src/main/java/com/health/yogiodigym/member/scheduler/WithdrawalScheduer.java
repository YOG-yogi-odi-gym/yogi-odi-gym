package com.health.yogiodigym.member.scheduler;

import com.health.yogiodigym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class WithdrawalScheduer {

    private final MemberService memberService;

    @Scheduled(cron = "0 0 0 * * *")
    public void withdrawalMember() {
        memberService.withdrawInactiveMembers();
    }
}
