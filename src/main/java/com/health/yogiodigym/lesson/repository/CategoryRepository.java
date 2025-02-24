package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.lesson.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCode(String code);
}