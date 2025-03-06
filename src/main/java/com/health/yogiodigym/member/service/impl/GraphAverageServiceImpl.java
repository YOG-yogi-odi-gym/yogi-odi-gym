package com.health.yogiodigym.member.service.impl;

import com.health.yogiodigym.calendar.repository.CalendarExerciseRepository;
import com.health.yogiodigym.member.entity.GraphAverage;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.GraphAverageRepository;
import com.health.yogiodigym.member.service.GraphAverageService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphAverageServiceImpl implements GraphAverageService {

    private final GraphAverageRepository graphAverageRepository;
    private final CalendarExerciseRepository calendarExerciseRepository;

    @Override
    public List<Float> getMyPreviousDateCalorie(Member member) {
        return calendarExerciseRepository.findAvgCalorieByDate(member, LocalDate.now().minusDays(4), LocalDate.now().minusDays(1));
    }

    @Override
    public List<Float> getCalorieAverage() {
        return graphAverageRepository.findByDateBetween(LocalDate.now().minusDays(4), LocalDate.now().minusDays(1))
                .stream()
                .map(GraphAverage::getCalorieAvg)
                .toList();
    }

    @Override
    public List<Float> getMyPreviousDateExerciseTime(Member member) {
        return calendarExerciseRepository.findAvgExerciseTimeByDate(member, LocalDate.now().minusDays(4), LocalDate.now().minusDays(1));
    }

    @Override
    public List<Float> getExerciseTimeAverage() {
        return graphAverageRepository.findByDateBetween(LocalDate.now().minusDays(4), LocalDate.now().minusDays(1))
                .stream()
                .map(GraphAverage::getExerciseAvg)
                .toList();
    }

    @Override
    public void updateGraphAverage() {

    }
}
