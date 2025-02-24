package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.UpdateRequest;
import com.health.yogiodigym.calendar.dto.CalendarExerciseDto.InsertRequest;
import com.health.yogiodigym.calendar.entity.CalendarExercise;
import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.entity.Member;
import com.health.yogiodigym.calendar.repository.CalendarExerciseRepository;
import com.health.yogiodigym.calendar.repository.DataExerciseRepository;
import com.health.yogiodigym.calendar.repository.MemberRepository;
import com.health.yogiodigym.calendar.service.CalendarExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarExerciseServiceImpl implements CalendarExerciseService {

    @Autowired
    private CalendarExerciseRepository calendarExerciseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DataExerciseRepository dataExerciseRepository;

    @Override
    public List<CalendarExercise> findByMemberId(Long memberId) {
        return calendarExerciseRepository.findByMemberId(memberId);
    }

    @Override
    public List<CalendarExercise> findByDateAndMemberId(LocalDate selectedDate, Long memberId) {
        return calendarExerciseRepository.findByDateAndMemberId(selectedDate,memberId);
    }

    @Override
    public CalendarExercise PostExerciseByDate(InsertRequest dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        DataExercise dataExercise = dataExerciseRepository.findByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("운동을 찾을 수 없습니다."));

        CalendarExercise exercise = new CalendarExercise();
        exercise.setName(dto.getName());
        exercise.setTime(dto.getTime());
        exercise.setCalories(dto.getCalories());
        exercise.setDate(dto.getRequestedDate());
        exercise.setMember(member);
        exercise.setDataExercise(dataExercise);

        return calendarExerciseRepository.save(exercise);
    }

    @Override
    public CalendarExercise PutExerciseByDate(UpdateRequest dto) {

        CalendarExercise exercise = calendarExerciseRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 운동일정 이 존재하지 않습니다."));

        if (!exercise.getDataExercise().getName().equals(dto.getName())) {
            DataExercise dataExercise = dataExerciseRepository.findByName(dto.getName())
                    .orElseThrow(() -> new RuntimeException("운동을 찾을 수 없습니다: "));
            exercise.setDataExercise(dataExercise);
        }

        if (!exercise.getMember().getId().equals(dto.getMemberId())) {
            Member member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
            exercise.setMember(member);
        }

        exercise.setName(dto.getName());
        exercise.setTime(dto.getTime());
        exercise.setCalories(dto.getCalories());
        exercise.setDate(dto.getRequestedDate());

        return calendarExerciseRepository.save(exercise);
    }

    @Override
    public void DeleteExerciseByDate(Long id) {
        calendarExerciseRepository.deleteById(id);
    }
}
