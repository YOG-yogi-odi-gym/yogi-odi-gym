package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.*;
import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarFoodRepository;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import com.health.yogiodigym.calendar.service.CalendarFoodService;
import com.health.yogiodigym.common.exception.DataFoodNotFoundException;
import com.health.yogiodigym.common.exception.FoodNotFoundException;
import com.health.yogiodigym.common.exception.MemberNotFoundException;
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
    public List<SelectRequest> findByMemberId(Long memberId) {
        return calendarFoodRepository.findByMemberId(memberId)
                .stream()
                .map(food -> SelectRequest.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .hundredGram(food.getHundredGram())
                        .calories(food.getCalories())
                        .date(food.getDate())
                        .memberId(food.getMember().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectRequest> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarFoodRepository.findByDateAndMemberId(selectedDate,memberId)
                .stream()
                .map(food -> SelectRequest.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .hundredGram(food.getHundredGram())
                        .calories(food.getCalories())
                        .date(food.getDate())
                        .memberId(food.getMember().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public void postFoodByDate(InsertRequest dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));

        DataFood dataFood = dataFoodRepository.findByName(dto.getName())
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
    public void putFoodByDate(UpdateRequest dto) {

        CalendarFood food = calendarFoodRepository.findById(dto.getId())
                .orElseThrow(() -> new FoodNotFoundException());

        DataFood dataFood=food.getDataFood();
        if (!food.getDataFood().getName().equals(dto.getName())) {
            dataFood = dataFoodRepository.findByName(dto.getName())
                    .orElseThrow(() -> new DataFoodNotFoundException());
        }

        Member member = food.getMember();
        if (!member.getId().equals(dto.getMemberId())) {
            member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));
        }

        CalendarFood updatedFood = CalendarFood.builder()
                .id(food.getId())
                .dataFood(dataFood)
                .member(member)
                .name(dto.getName())
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
