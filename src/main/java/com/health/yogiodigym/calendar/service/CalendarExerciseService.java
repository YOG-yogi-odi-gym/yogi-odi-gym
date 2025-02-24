package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.CalendarExercise;
import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarExerciseRepository;
import com.health.yogiodigym.calendar.repository.DataExerciseRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarExerciseService {

    @Autowired
    private CalendarExerciseRepository calendarExerciseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DataExerciseRepository dataExerciseRepository;

    public List<CalendarExercise> findByMemberId(Long memberId) {
        return calendarExerciseRepository.findByMemberId(memberId);
    }

    public List<CalendarExercise> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarExerciseRepository.findByDateAndMemberId(selectedDate,memberId);
    }

    public CalendarExercise PostExerciseByDate(String name, Float time, Float calories, LocalDate selectedDate, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        DataExercise dataExercise = dataExerciseRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("운동을 찾을 수 없습니다: "));

        CalendarExercise exercise = new CalendarExercise();
        exercise.setName(name);
        exercise.setTime(time);
        exercise.setCalories(calories);
        exercise.setDate(selectedDate);
        exercise.setMember(member);
        exercise.setDataExercise(dataExercise);

        return calendarExerciseRepository.save(exercise);
    }

    public CalendarExercise PutExerciseByDate(Long id, String name, Float time, Float calories, LocalDate selectedDate, Long memberId) {

        CalendarExercise exercise = calendarExerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 운동일정 이 존재하지 않습니다."));

        if (!exercise.getDataExercise().getName().equals(name)) {
            DataExercise dataExercise = dataExerciseRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("운동을 찾을 수 없습니다: "));
            exercise.setDataExercise(dataExercise);
        }

        if (!exercise.getMember().getId().equals(memberId)) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
            exercise.setMember(member);
        }

        exercise.setName(name);
        exercise.setTime(time);
        exercise.setCalories(calories);
        exercise.setDate(selectedDate);

        return calendarExerciseRepository.save(exercise);
    }

    public void DeleteExerciseByDate(Long id) {
        calendarExerciseRepository.deleteById(id);
    }
}
