package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.dto.DataExerciseDto;
import com.health.yogiodigym.calendar.dto.DataExerciseDto.*;
import com.health.yogiodigym.calendar.dto.DataFoodDto;
import com.health.yogiodigym.calendar.repository.DataExerciseRepository;
import com.health.yogiodigym.calendar.service.DataExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DataExerciseServiceImpl implements DataExerciseService {

    private final DataExerciseRepository dataExerciseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DataExerciseDto> findAll() {
        return dataExerciseRepository.findAll()
                .stream()
                .map(dataExercises -> DataExerciseDto.builder()
                        .id(dataExercises.getId())
                        .name(dataExercises.getName())
                        .energyConsumption(dataExercises.getEnergyConsumption())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataExerciseDto> findByNameContainingIgnoreCase(String name) {
        return dataExerciseRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(dataExercise -> DataExerciseDto.builder()
                        .id(dataExercise.getId())
                        .name(dataExercise.getName())
                        .energyConsumption(dataExercise.getEnergyConsumption())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
