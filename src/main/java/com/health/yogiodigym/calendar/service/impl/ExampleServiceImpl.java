package com.health.yogiodigym.calendar.service.impl;

import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import com.health.yogiodigym.calendar.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private final DataFoodRepository dataFoodRepository;

    @Override
    public void add() {

    }
}
