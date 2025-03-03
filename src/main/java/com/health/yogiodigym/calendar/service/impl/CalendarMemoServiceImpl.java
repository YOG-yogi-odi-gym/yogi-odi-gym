package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.CalendarMemoDto.*;
import com.health.yogiodigym.calendar.entity.CalendarMemo;
import com.health.yogiodigym.calendar.repository.CalendarMemoRepository;
import com.health.yogiodigym.calendar.service.CalendarMemoService;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
import com.health.yogiodigym.common.exception.MemoNotFoundException;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class CalendarMemoServiceImpl implements CalendarMemoService {

    private final CalendarMemoRepository calendarMemoRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalendarMemoSelectDto> findByMemberId(Long memberId) {
        return calendarMemoRepository.findByMemberId(memberId)
                .stream()
                .map(CalendarMemoSelectDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarMemoSelectDto> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarMemoRepository.findByDateAndMemberId(selectedDate,memberId)
                .stream()
                .map(CalendarMemoSelectDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void postMemoByDate(CalendarMemoInsertDto dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));

        CalendarMemo postMemo = CalendarMemo.builder()
                .title(dto.getTitle())
                .context(dto.getContext())
                .date(dto.getDate())
                .member(member)
                .build();

        calendarMemoRepository.save(postMemo);
    }

    @Override
    public void putMemoByDate(CalendarMemoUpdateDto dto) {

        CalendarMemo memo = calendarMemoRepository.findById(dto.getId())
                .orElseThrow(() -> new MemoNotFoundException());

        Member member = memo.getMember();
        if (!member.getId().equals(dto.getMemberId())) {
            member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));
        }

        CalendarMemo updateMemo = CalendarMemo.builder()
                .id(memo.getId())
                .title(dto.getTitle())
                .context(dto.getContext())
                .date(dto.getDate())
                .member(member)
                .build();

        calendarMemoRepository.save(updateMemo);
    }

    @Override
    public void deleteMemoByDate(Long id) {

        calendarMemoRepository.deleteById(id);
    }

}
