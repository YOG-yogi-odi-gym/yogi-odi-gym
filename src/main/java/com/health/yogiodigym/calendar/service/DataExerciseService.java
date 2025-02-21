package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.repository.DataExerciseRepository;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataExerciseService {

    @Autowired
    private DataExerciseRepository dataExerciseRepository;

    // 음식명 검색 메서드
    public List<DataExercise> searchExerciseByName(String name) {
        return dataExerciseRepository.findByNameContainingIgnoreCase(name);
    }

    public List<DataExercise> searchExerciseAll() {
        return dataExerciseRepository.findAll();
    }


}
