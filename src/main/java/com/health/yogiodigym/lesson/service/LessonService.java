package com.health.yogiodigym.lesson.service;

import com.health.yogiodigym.lesson.dto.CategoryDto;
import com.health.yogiodigym.lesson.dto.LessonDto.*;
import com.health.yogiodigym.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {

    Page<LessonSearchDto> searchLessons(String keyword, String column, Integer days, List<Long> categories, Pageable pageable);

    LessonDetailDto findLessonById(Long lessonId);

    void editLesson(LessonEditDto lessonDto);

    void registerLesson(LessonRequestDto lessonDto, Member master);

    boolean[] daysSelected(int days);

    List<CategoryDto> getCategoriesByCode(String code);

    Page<LessonSearchDto> searchMyLessons(Long id, String lessonKeyword, String searchColumn, Integer days, List<Long> categories, Pageable pageable);

    String getRoomIdByLessonId(Long lessonId);
}