package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.service.service.AdminCategoryService;
import com.health.yogiodigym.lesson.dto.CategoryDto;
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
}
