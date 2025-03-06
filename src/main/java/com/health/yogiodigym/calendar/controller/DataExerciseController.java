package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.DataExerciseDto;
import com.health.yogiodigym.calendar.service.DataExerciseService;
import com.health.yogiodigym.calendar.service.impl.DataExerciseServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class DataExerciseController {

    private final DataExerciseServiceImpl dataExerciseServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<DataExerciseDto> dataExercises= dataExerciseServiceImpl.findAll();

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_DATA_EXERCISE_SUCCESS.getMessage(), dataExercises));
    }


    @GetMapping("/search")
    public ResponseEntity<?> findByNameContainingIgnoreCase(String name) {
        List<DataExerciseDto> dataExercise=  dataExerciseServiceImpl.findByNameContainingIgnoreCase(name);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_ONE_DATA_EXERCISE_SUCCESS.getMessage(), dataExercise));
    }
}
