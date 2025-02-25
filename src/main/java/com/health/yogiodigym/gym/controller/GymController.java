package com.health.yogiodigym.gym.controller;

import com.health.yogiodigym.gym.entity.DataGym;
import com.health.yogiodigym.gym.service.GymService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/gyms")
public class GymController {

    private final GymService gymService;

    public GymController(GymService gymService) {
        this.gymService = gymService;
    }

    @GetMapping("/api/search")
    public ResponseEntity<?> searchGyms(@RequestParam(required = false) String gymKeyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "name") String searchColumn) {
        Page<DataGym> gyms = gymService.findByGymSearch(gymKeyword, page, size, searchColumn);
        return ResponseEntity.ok(gyms);
    }
}