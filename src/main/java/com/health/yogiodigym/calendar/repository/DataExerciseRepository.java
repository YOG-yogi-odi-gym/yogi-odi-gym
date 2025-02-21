package com.health.yogiodigym.calendar.repository;

import com.health.yogiodigym.calendar.entity.DataExercise;
import com.health.yogiodigym.calendar.entity.DataFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataExerciseRepository extends JpaRepository<DataExercise, Long> {

    // 운동명으로 검색하는 메서드 (LIKE 검색)
    List<DataExercise> findByNameContainingIgnoreCase(String name);

    Optional<DataExercise> findByName(String name);
}
