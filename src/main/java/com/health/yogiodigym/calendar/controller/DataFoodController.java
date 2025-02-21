package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.entity.DataFood;
import com.health.yogiodigym.calendar.service.DataFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "*") // CORS 문제 방지
public class DataFoodController {

    @Autowired
    private DataFoodService dataFoodService;

    @GetMapping("/all")
    public List<DataFood> getAll() {
        return dataFoodService.searchFoodAll();
    }

    // 음식명 검색 API (Ajax 요청을 처리)
    @GetMapping("/search")
    public List<DataFood> searchFood(@RequestParam String name) {
        return dataFoodService.searchFoodByName(name);
    }
}
