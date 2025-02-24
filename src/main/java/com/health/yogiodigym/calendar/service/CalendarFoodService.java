package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.CalendarFood;
import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarFoodRepository;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarFoodService {

    @Autowired
    private CalendarFoodRepository calendarFoodRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DataFoodRepository dataFoodRepository;

    public List<CalendarFood>  findByMemberId(Long memberId) {
        return calendarFoodRepository.findByMemberId(memberId);
    }

    public List<CalendarFood> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarFoodRepository.findByDateAndMemberId(selectedDate,memberId);
    }

    public CalendarFood PostFoodByDate(String name, Float hundredGram, Float calories, LocalDate selectedDate, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        DataFood dataFood = dataFoodRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("음식을 찾을 수 없습니다: "));

        CalendarFood food = new CalendarFood();
        food.setName(name);
        food.setHundredGram(hundredGram);
        food.setCalories(calories);
        food.setDate(selectedDate);
        food.setMember(member);
        food.setDataFood(dataFood);

        return calendarFoodRepository.save(food);
    }

    public CalendarFood PutFoodByDate(Long id, String name, Float hundredGram, Float calories, LocalDate selectedDate, Long memberId) {
        // 기존 메모 조회

        CalendarFood food = calendarFoodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 식단이 존재하지 않습니다."));

        if (!food.getDataFood().getName().equals(name)) {
            DataFood dataFood = dataFoodRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("음식을 찾을 수 없습니다: "));
            food.setDataFood(dataFood);
        }

        if (!food.getMember().getId().equals(memberId)) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
            food.setMember(member);
        }

        food.setName(name);
        food.setHundredGram(hundredGram);
        food.setCalories(calories);
        food.setDate(selectedDate);

        return calendarFoodRepository.save(food);
    }

    public void DeleteFoodByDate(Long id) {
        calendarFoodRepository.deleteById(id);
    }
}
