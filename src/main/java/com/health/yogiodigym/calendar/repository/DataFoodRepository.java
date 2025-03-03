package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.DataFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataFoodRepository extends JpaRepository<DataFood, Long> {

    List<DataFood> findByNameContainingIgnoreCase(String name);

}
