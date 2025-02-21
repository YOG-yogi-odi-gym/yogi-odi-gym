package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.service.DataExerciseService;
import com.health.yogiodigym.calendar.service.DataFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@CrossOrigin(origins = "*") // CORS 문제 방지
public class DataExerciseController {

    @Autowired
    private DataExerciseService dataExerciseService;

    @GetMapping("/all")
    public List<DataExercise> getAll() {
        return dataExerciseService.searchExerciseAll();
    }

    // 음식명 검색 API (Ajax 요청을 처리)
    @GetMapping("/search")
    public List<DataExercise> searchFood(@RequestParam String name) {
        return dataExerciseService.searchExerciseByName(name);
    }
}
