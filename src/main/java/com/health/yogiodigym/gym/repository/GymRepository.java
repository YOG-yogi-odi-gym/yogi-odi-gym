package com.health.yogiodigym.gym.repository;

import com.health.yogiodigym.gym.entity.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
    Page<Gym> findAll(Pageable pageable);
    Page<Gym> findByNameContaining(String gymKeyword, Pageable pageable);
    Page<Gym> findByOldAddressContaining(String gymKeyword, Pageable pageable);
}