package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.DataExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataExerciseRepository extends JpaRepository<DataExercise, Long> {

    // ContainingIgnoreCase : 대소문자 무시
    List<DataExercise> findByNameContainingIgnoreCase(String name);

    Optional<DataExercise> findByName(String name);
}
