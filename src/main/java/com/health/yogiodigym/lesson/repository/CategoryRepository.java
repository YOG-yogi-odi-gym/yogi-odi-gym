package com.health.yogiodigym.lesson.repository;

import com.health.yogiodigym.lesson.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
