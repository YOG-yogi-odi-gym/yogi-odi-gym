package com.health.yogiodigym.calendar.service;


import com.health.yogiodigym.calendar.dto.DataFoodDto.*;


import java.util.List;

public interface DataFoodService {

    List<SelectRequest> findByNameContainingIgnoreCase(String name);

    List<SelectRequest>  findAll();

}
