package com.health.yogiodigym.admin.service.service;

import com.health.yogiodigym.lesson.dto.CategoryDto;

import java.util.List;

public interface AdminCategoryService {

    List<CategoryDto> findAll();

    void deleteAllById(List<Long> ids);

}
