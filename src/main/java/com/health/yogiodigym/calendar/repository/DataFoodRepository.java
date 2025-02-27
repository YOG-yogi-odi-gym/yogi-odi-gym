package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.DataFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataFoodRepository extends JpaRepository<DataFood, Long> {

    // ContainingIgnoreCase : 대소문자 무시
    List<DataFood> findByNameContainingIgnoreCase(String name);

    Optional<DataFood> findByName(String name);
}
