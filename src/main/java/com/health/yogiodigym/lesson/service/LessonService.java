package com.health.yogiodigym.lesson.service;

import com.health.yogiodigym.lesson.dto.LessonDetailDto;
import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.lesson.dto.LessonEditDto;
import com.health.yogiodigym.lesson.dto.LessonRequestDto;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CategoryRepository categoryRepository;
    private final LessonEnrollmentRepository lessonEnrollmentRepository;

    public LessonService(LessonRepository lessonRepository,
                         CategoryRepository categoryRepository,
                         LessonEnrollmentRepository lessonEnrollmentRepository) {
        this.lessonRepository = lessonRepository;
        this.categoryRepository = categoryRepository;
        this.lessonEnrollmentRepository = lessonEnrollmentRepository;
    }

    @Transactional
    public Page<LessonDto> searchLessons(String keyword, String column, Integer days, List<Long> categories, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Lesson> lessons;

        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasDays = days != null;
        boolean hasCategories = categories != null && !categories.isEmpty();

        if (!hasKeyword && !hasDays && !hasCategories) {
            lessons = lessonRepository.findAll(pageable);
        } else if (hasCategories) {
            lessons = lessonRepository.searchLessonsByCategories(keyword, column, days, categories, pageable);
        } else {
            lessons = lessonRepository.searchLessons(keyword, column, days, pageable);
        }

        return lessons.map(LessonDto::new);
    }

    @Transactional
    public Page<LessonDto> findByTitleContaining(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Lesson> lessons;
        if (title == null || title.isEmpty()) {
            lessons = lessonRepository.findAll(pageable);
        } else {
            lessons = lessonRepository.findByTitleContaining(title, pageable);
        }
        return lessons.map(LessonDto::new);
    }

    @Transactional
    public LessonDetailDto findLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));

        return new LessonDetailDto(lesson);
    }

    public void editLesson(LessonEditDto lessonDto) {
        Lesson lesson = lessonRepository.findById(lessonDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(lessonDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        lesson.updateLesson(lessonDto, category);
        lessonRepository.save(lesson);
    }

    public void registerLesson(LessonRequestDto lessonDto, Member master) {
        Category category = categoryRepository.findById(lessonDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        Lesson lesson = Lesson.builder()
                .title(lessonDto.getTitle())
                .category(category)
                .days(lessonDto.getDays())
                .location(lessonDto.getLocation())
                .detailedLocation(lessonDto.getDetailedLocation())
                .latitude(lessonDto.getLatitude())
                .longitude(lessonDto.getLongitude())
                .startTime(lessonDto.getStartTime())
                .endTime(lessonDto.getEndTime())
                .startDay(lessonDto.getStartDay())
                .endDay(lessonDto.getEndDay())
                .description(lessonDto.getDescription())
                .max(lessonDto.getMax())
                .current(1)
                .master(master)
                .build();

        lessonRepository.save(lesson);

        LessonEnrollment enrollment = LessonEnrollment.builder()
                .lesson(lesson)
                .member(master)
                .build();

        lessonEnrollmentRepository.save(enrollment);
    }
}