package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.*;
import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.repository.CalendarFoodRepository;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import com.health.yogiodigym.calendar.service.CalendarFoodService;
import com.health.yogiodigym.common.exception.DataFoodNotFoundException;
import com.health.yogiodigym.common.exception.FoodNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
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
public class CalendarFoodServiceImpl implements CalendarFoodService {

    private final CalendarFoodRepository calendarFoodRepository;

    private final MemberRepository memberRepository;

    private final DataFoodRepository dataFoodRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalendarFoodSelectDto> findByMemberId(Long memberId) {
        return calendarFoodRepository.findByMemberId(memberId)
                .stream()
                .map(CalendarFoodSelectDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarFoodSelectDto> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarFoodRepository.findByDateAndMemberId(selectedDate,memberId)
                .stream()
                .map(CalendarFoodSelectDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void postFoodByDate(CalendarFoodInsertDto dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));

        DataFood dataFood = dataFoodRepository.findById(dto.getFoodId())
                .orElseThrow(() -> new DataFoodNotFoundException());

        CalendarFood postFood = CalendarFood.builder()
                .name(dto.getName())
                .hundredGram(dto.getHundredGram())
                .calories(dto.getCalories())
                .date(dto.getDate())
                .member(member)
                .dataFood(dataFood)
                .build();

        calendarFoodRepository.save(postFood);
    }

    @Override
    public void putFoodByDate(CalendarFoodUpdateDto dto) {

        CalendarFood food = calendarFoodRepository.findById(dto.getId())
                .orElseThrow(() -> new FoodNotFoundException());

        DataFood dataFood=food.getDataFood();
        if (!dataFood.getId().equals(dto.getFoodId())) {
            dataFood = dataFoodRepository.findById(dto.getFoodId())
                    .orElseThrow(() -> new DataFoodNotFoundException());
        }

        Member member = food.getMember();
        if (!member.getId().equals(dto.getMemberId())) {
            member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));
        }

        CalendarFood updatedFood = CalendarFood.builder()
                .id(food.getId())
                .name(dto.getName())
                .dataFood(dataFood)
                .member(member)
                .hundredGram(dto.getHundredGram())
                .calories(dto.getCalories())
                .date(dto.getDate())
                .build();
        calendarFoodRepository.save(updatedFood);
    }

    @Override
    public void deleteFoodByDate(Long id) {

        calendarFoodRepository.deleteById(id);
    }
}
