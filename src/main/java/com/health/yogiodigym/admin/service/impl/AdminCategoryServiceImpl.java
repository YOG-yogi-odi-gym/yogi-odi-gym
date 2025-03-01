package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.service.service.AdminCategoryService;
import com.health.yogiodigym.lesson.dto.CategoryDto;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<CategoryDto> findAll() {

        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        categoryRepository.deleteAllById(ids);
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {

        Category category = Category.builder()
                .name(categoryDto.getName())
                .code(categoryDto.getCode())
                .build();

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. id=" + categoryDto.getId()));

        category = Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .code(categoryDto.getCode())
                .build();

        categoryRepository.save(category);
    }
}
