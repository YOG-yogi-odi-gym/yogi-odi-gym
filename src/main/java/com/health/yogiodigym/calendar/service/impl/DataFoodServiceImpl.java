package com.health.yogiodigym.calendar.service.impl;


import com.health.yogiodigym.calendar.dto.DataFoodDto.*;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import com.health.yogiodigym.calendar.service.DataFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DataFoodServiceImpl implements DataFoodService {

    private final DataFoodRepository dataFoodRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SelectRequest> findAll() {

        return dataFoodRepository.findAll()
                .stream()
                .map(dataFoods -> SelectRequest.builder()
                        .id(dataFoods.getId())
                        .name(dataFoods.getName())
                        .calories(dataFoods.getCalories())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectRequest> findByNameContainingIgnoreCase(String name) {

        return dataFoodRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(dataFood -> SelectRequest.builder()
                        .id(dataFood.getId())
                        .name(dataFood.getName())
                        .calories(dataFood.getCalories())
                        .build()
                )
                .collect(Collectors.toList());
    }



}
