package com.health.yogiodigym.admin.controller;

import com.health.yogiodigym.admin.service.service.AdminCategoryService;
import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.lesson.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.ADMIN_CATEGORY_DELETE_SUCCESS;
import static com.health.yogiodigym.common.message.SuccessMessage.ADMIN_CATEGORY_INSERT_SUCCESS;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping(("/category/delete"))
    public ResponseEntity<?> adminDeleteCategory(@RequestBody List<Long> ids) {

        adminCategoryService.deleteAllById(ids);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ADMIN_CATEGORY_DELETE_SUCCESS.getMessage(), null));
    }

    @PostMapping("/category/insert")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

        adminCategoryService.saveCategory(categoryDto);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, ADMIN_CATEGORY_INSERT_SUCCESS.getMessage(), null));
    }
}
