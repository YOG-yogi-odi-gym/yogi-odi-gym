package com.health.yogiodigym.calendar.controller;

import com.health.yogiodigym.calendar.dto.DataFoodDto;
import com.health.yogiodigym.calendar.service.impl.DataFoodServiceImpl;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.*;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class DataFoodController {

    private final DataFoodServiceImpl dataFoodServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<DataFoodDto> datafoods= dataFoodServiceImpl.findAll();;

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_DATA_EXERCISE_SUCCESS.getMessage(), datafoods));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByNameContainingIgnoreCase(String name) {
        List<DataFoodDto> datafood=  dataFoodServiceImpl.findByNameContainingIgnoreCase(name);

        return ResponseEntity
                .ok()
                .body(new HttpResponse(HttpStatus.OK,GET_ONE_DATA_FOOD_SUCCESS.getMessage(), datafood));
    }
}
