package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.service.DataExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@CrossOrigin(origins = "*") // CORS 문제 방지
public class DataExerciseController {

    @Autowired
    private DataExerciseService dataExerciseService;

    @GetMapping("/all")
    public List<DataExercise> findAll() {
        return dataExerciseService.findAll();
    }

    // 음식명 검색 API (Ajax 요청을 처리)
    @GetMapping("/search")
    public List<DataExercise> findByNameContainingIgnoreCase(String name) {
        return dataExerciseService.findByNameContainingIgnoreCase(name);
    }
}
