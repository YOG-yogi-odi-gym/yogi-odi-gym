package com.health.yogiodigym.calendar.service;

import com.health.yogiodigym.calendar.dto.DataExerciseDto.*;


import java.util.List;

public interface DataExerciseService {

    List<SelectRequest> findByNameContainingIgnoreCase(String name);

    List<SelectRequest> findAll();

}
