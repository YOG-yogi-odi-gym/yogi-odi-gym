package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CaleadarMemoRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CallendarMemoService {

    @Autowired
    private CaleadarMemoRepository caleadarMemoRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 특정 회원(memberId) 기준으로 전체 메모 조회
    public List<CalendarMemo> findMemoById(Long memberId) {
        return caleadarMemoRepository.findMemoById(memberId);
    }

    public List<CalendarMemo> findMemoByDate(LocalDate selectedDate, Long memberId) {
        return caleadarMemoRepository.findMemoByDate(selectedDate, memberId);
    }

    public CalendarMemo PostMemoByDate(String title, String context, LocalDate selectedDate, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        CalendarMemo memo = new CalendarMemo();
        memo.setTitle(title);
        memo.setContext(context);
        memo.setDate(selectedDate);
        memo.setMember(member);

        return caleadarMemoRepository.save(memo);
    }

    public CalendarMemo PutMemoByDate(Long id, String title, String context, LocalDate selectedDate, Long memberId) {
        // 기존 메모 조회

        CalendarMemo memo = caleadarMemoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 존재하지 않습니다."));

        // memberId가 변경될 경우에만 새롭게 조회
        if (!memo.getMember().getId().equals(memberId)) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
            memo.setMember(member);
        }

        // 값 변경
        memo.setTitle(title);
        memo.setContext(context);
        memo.setDate(selectedDate);

        // 저장 후 반환
        return caleadarMemoRepository.save(memo);
    }


    public void DeleteMemoByDate(Long id) {
        caleadarMemoRepository.deleteById(id);
    }

}
