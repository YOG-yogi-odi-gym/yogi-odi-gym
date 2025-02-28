package com.health.yogiodigym.lesson.service.impl;

import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.service.ChatRoomService;
import com.health.yogiodigym.common.exception.CategoryNotFoundException;
import com.health.yogiodigym.common.exception.LessonNotFoundException;
import com.health.yogiodigym.lesson.dto.CategoryDto;
import com.health.yogiodigym.lesson.dto.LessonDto.*;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.lesson.repository.CategoryRepository;
import com.health.yogiodigym.lesson.repository.LessonEnrollmentRepository;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import com.health.yogiodigym.lesson.service.LessonService;
import com.health.yogiodigym.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CategoryRepository categoryRepository;
    private final LessonEnrollmentRepository lessonEnrollmentRepository;
    private final ChatRoomService chatRoomService;

    @Override
    @Transactional(readOnly = true)
    public Page<LessonSearchDto> searchLessons(String keyword, String column, Integer days, List<Long> categories, Pageable pageable) {
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

        return lessons.map(LessonSearchDto::new);
    }

    @Override
    public LessonDetailDto findLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        return new LessonDetailDto(lesson);
    }

    @Override
    public void editLesson(LessonEditDto lessonDto) {
        Lesson lesson = lessonRepository.findById(lessonDto.getId())
                .orElseThrow(() -> new LessonNotFoundException(lessonDto.getId()));

        Category category = categoryRepository.findById(lessonDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(lessonDto.getCategoryId()));

        lesson.updateLesson(lessonDto, category);
        lessonRepository.save(lesson);
    }

    @Override
    public void registerLesson(LessonRequestDto lessonDto, Member master) {
        Category category = categoryRepository.findById(lessonDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(lessonDto.getCategoryId()));

        ChatRoom chatRoom = chatRoomService.createChatRoom(master,true);

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
                .chatRoom(chatRoom)
                .build();

        lessonRepository.save(lesson);

        LessonEnrollment enrollment = LessonEnrollment.builder()
                .lesson(lesson)
                .member(master)
                .build();

        lessonEnrollmentRepository.save(enrollment);
    }

    @Override
    public boolean[] daysSelected(int days) {
        boolean[] daysSelected = new boolean[7];
        for (int i = 0; i < 7; i++) {
            daysSelected[i] = (days & (1 << i)) != 0;
        }
        return daysSelected;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoriesByCode(String code) {
        List<Category> categories = categoryRepository.findByCode(code);

        return categories.stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }
}