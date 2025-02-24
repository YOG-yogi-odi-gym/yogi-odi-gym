package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarMemoRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarMemoService {

    @Autowired
    private CalendarMemoRepository calendarMemoRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<CalendarMemo> findByMemberId(Long memberId) {
        return calendarMemoRepository.findByMemberId(memberId);
    }

    public List<CalendarMemo> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarMemoRepository.findByDateAndMemberId(selectedDate, memberId);
    }

    public CalendarMemo PostMemoByDate(String title, String context, LocalDate selectedDate, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        CalendarMemo memo = new CalendarMemo();
        memo.setTitle(title);
        memo.setContext(context);
        memo.setDate(selectedDate);
        memo.setMember(member);

        return calendarMemoRepository.save(memo);
    }

    public CalendarMemo PutMemoByDate(Long id, String title, String context, LocalDate selectedDate, Long memberId) {

        CalendarMemo memo = calendarMemoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 존재하지 않습니다."));

        if (!memo.getMember().getId().equals(memberId)) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
            memo.setMember(member);
        }

        memo.setTitle(title);
        memo.setContext(context);
        memo.setDate(selectedDate);

        return calendarMemoRepository.save(memo);
    }

    public void DeleteMemoByDate(Long id) {
        calendarMemoRepository.deleteById(id);
    }

}
