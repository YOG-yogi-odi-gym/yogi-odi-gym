package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.CalendarFoodDto.InsertRequest;
import com.health.yogiodigym.calendar.dto.CalendarFoodDto.UpdateRequest;
import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarFoodRepository;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import com.health.yogiodigym.calendar.service.CalendarFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarFoodServiceImpl implements CalendarFoodService {

    private final CalendarFoodRepository calendarFoodRepository;

    private final MemberRepository memberRepository;

    private final DataFoodRepository dataFoodRepository;

    @Override
    public List<CalendarFood> findByMemberId(Long memberId) {
        return calendarFoodRepository.findByMemberId(memberId);
    }

    @Override
    public List<CalendarFood> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarFoodRepository.findByDateAndMemberId(selectedDate,memberId);
    }

    @Override
    public CalendarFood PostFoodByDate(InsertRequest dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        DataFood dataFood = dataFoodRepository.findByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("음식을 찾을 수 없습니다: "));

        CalendarFood food = CalendarFood.builder()
                .name(dto.getName())
                .hundredGram(dto.getHundredGram())
                .calories(dto.getCalories())
                .date(dto.getRequestedDate())
                .member(member)
                .dataFood(dataFood)
                .build();

        return calendarFoodRepository.save(food);
    }

    @Override
    public CalendarFood PutFoodByDate(UpdateRequest dto) {
        // 기존 메모 조회

        CalendarFood food = calendarFoodRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 식단이 존재하지 않습니다."));

        DataFood dataFood=food.getDataFood();
        if (!food.getDataFood().getName().equals(dto.getName())) {
            dataFood = dataFoodRepository.findByName(dto.getName())
                    .orElseThrow(() -> new RuntimeException("음식을 찾을 수 없습니다: "));
        }

        Member member = food.getMember();
        if (!member.getId().equals(dto.getMemberId())) {
            member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        }

        CalendarFood updatedFood = CalendarFood.builder()
                .id(food.getId())
                .dataFood(dataFood)
                .member(member)
                .name(dto.getName())
                .hundredGram(dto.getHundredGram())
                .calories(dto.getCalories())
                .date(dto.getRequestedDate())
                .build();

        return calendarFoodRepository.save(updatedFood);
    }

    @Override
    public void DeleteFoodByDate(Long id) {
        calendarFoodRepository.deleteById(id);
    }
}
