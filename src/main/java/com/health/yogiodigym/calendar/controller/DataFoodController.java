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
    public List<DataFood> findAll() {
        return dataFoodService.findAll();
    }

    @GetMapping("/search")
    public List<DataFood> findByNameContainingIgnoreCase(@RequestParam("name") String name) {
        return dataFoodService.findByNameContainingIgnoreCase(name);
    }
}
