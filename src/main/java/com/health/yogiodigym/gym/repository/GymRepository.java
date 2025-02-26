package com.health.yogiodigym.gym.repository;

import com.health.yogiodigym.gym.entity.DataGym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<DataGym, Long> {

    Page<DataGym> findAll(Pageable pageable);

    Page<DataGym> findByNameContaining(String gymKeyword, Pageable pageable);

    Page<DataGym> findByOldAddressContaining(String gymKeyword, Pageable pageable);
}