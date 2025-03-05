package com.health.yogiodigym.member.repository;

import com.health.yogiodigym.member.entity.GraphAverage;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphAverageRepository extends JpaRepository<GraphAverage, Long> {
    List<GraphAverage> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
