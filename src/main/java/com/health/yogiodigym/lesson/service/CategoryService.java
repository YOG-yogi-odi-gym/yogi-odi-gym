package com.health.yogiodigym.lesson.service;

import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}