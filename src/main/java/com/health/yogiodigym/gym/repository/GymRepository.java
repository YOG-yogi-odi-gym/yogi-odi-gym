package com.health.yogiodigym.gym.repository;

import com.health.yogiodigym.gym.entity.DataGym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GymRepository extends JpaRepository<DataGym, Long> {

    Page<DataGym> findAll(Pageable pageable);

    @Query("SELECT g FROM DataGym g WHERE " +
            "(:gymKeyword IS NULL OR :gymKeyword = '' OR " +
            "(CASE WHEN :searchColumn = 'name' THEN g.name ELSE g.oldAddress END) LIKE %:gymKeyword%)")
    Page<DataGym> findByKeywordAndColumn(@Param("gymKeyword") String gymKeyword,
                                         @Param("searchColumn") String searchColumn,
                                         Pageable pageable);
}