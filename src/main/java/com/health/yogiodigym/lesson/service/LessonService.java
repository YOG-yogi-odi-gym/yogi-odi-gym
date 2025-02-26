package com.health.yogiodigym.lesson.service;

import com.health.yogiodigym.lesson.dto.CategoryDto;
import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface LessonService {

    Page<LessonDto> searchLessons(String keyword, String column, Integer days, List<Long> categories, Pageable pageable);

    LessonDto.Detail findLessonById(Long lessonId);

    void editLesson(LessonDto.Edit lessonDto);

    void registerLesson(LessonDto.Request lessonDto, Member master);

    boolean[] daysSelected(int days);

    List<CategoryDto> getCategoriesByCode(String code);
}