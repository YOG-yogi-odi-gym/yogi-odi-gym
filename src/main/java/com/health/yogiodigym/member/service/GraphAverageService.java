package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.entity.Member;
import java.util.List;

public interface GraphAverageService {
    List<Float> getMyPreviousDateCalorie(Member member);

    List<Float> getCalorieAverage();

    List<Float> getMyPreviousDateExerciseTime(Member member);

    List<Float> getExerciseTimeAverage();

    void updateGraphAverage();
}
