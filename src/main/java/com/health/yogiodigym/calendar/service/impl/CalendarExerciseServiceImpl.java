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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarExerciseServiceImpl implements CalendarExerciseService {
    
    private final CalendarExerciseRepository calendarExerciseRepository;

    private final MemberRepository memberRepository;

    private final DataExerciseRepository dataExerciseRepository;

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

        CalendarExercise exercise = CalendarExercise.builder()
                .name(dto.getName())
                .time(dto.getTime())
                .calories(dto.getCalories())
                .date(dto.getRequestedDate())
                .member(member)
                .dataExercise(dataExercise)
                .build();

        return calendarExerciseRepository.save(exercise);
    }



@Override
public CalendarExercise PutExerciseByDate(UpdateRequest dto) {

    CalendarExercise exercise = calendarExerciseRepository.findById(dto.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 운동일정이 존재하지 않습니다."));

    DataExercise dataExercise = exercise.getDataExercise();
    if (!dataExercise.getName().equals(dto.getName())) {
        dataExercise = dataExerciseRepository.findByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("운동을 찾을 수 없습니다: " ));
    }

    Member member = exercise.getMember();
    if (!member.getId().equals(dto.getMemberId())) {
        member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    CalendarExercise updatedExercise = CalendarExercise.builder()
            .id(exercise.getId())
            .dataExercise(dataExercise)
            .member(member)
            .name(dto.getName())
            .time(dto.getTime())
            .calories(dto.getCalories())
            .date(dto.getRequestedDate())
            .build();

    return calendarExerciseRepository.save(updatedExercise);
}

    @Override
    public void DeleteExerciseByDate(Long id) {
        calendarExerciseRepository.deleteById(id);
    }
}
