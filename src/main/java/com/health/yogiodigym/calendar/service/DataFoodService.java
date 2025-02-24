package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.repository.DataFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataFoodService {

    @Autowired
    private DataFoodRepository dataFoodRepository;

    // 음식명 검색 메서드
    public List<DataFood> findByNameContainingIgnoreCase(String name) {
        return dataFoodRepository.findByNameContainingIgnoreCase(name);
    }

    public List<DataFood> findAll() {
        return dataFoodRepository.findAll();
    }


}
