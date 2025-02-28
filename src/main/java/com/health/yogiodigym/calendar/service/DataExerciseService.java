package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.repository.DataExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataExerciseService {

    @Autowired
    private DataExerciseRepository dataExerciseRepository;

    public List<DataExercise> findByNameContainingIgnoreCase(String name) {
        return dataExerciseRepository.findByNameContainingIgnoreCase(name);
    }

    public List<DataExercise> findAll() {
        return dataExerciseRepository.findAll();
    }


}
