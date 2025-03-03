package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.DataExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataExerciseRepository extends JpaRepository<DataExercise, Long> {

    List<DataExercise> findByNameContainingIgnoreCase(String name);

}
