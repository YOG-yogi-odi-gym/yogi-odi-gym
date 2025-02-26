package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.LessonDto.*;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminLessonServiceImpl implements AdminLessonService {

    private final LessonRepository lessonRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponseDto> getAllLessons() {

        List<Lesson> lessons = lessonRepository.findAll();

        List<LessonResponseDto> lessonDtos = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDtos.add(LessonResponseDto.from(lesson));
        }
        return lessonDtos;
//      return lessons.stream().map(l -> LessonDto.LessonResponseDto.from(l)).collect(Collectors.toList());
    }

    public List<LessonResponseDto> searchLessons(String lessonKeyword) {

        List<Lesson> lessons = lessonRepository.searchLessons(lessonKeyword);

        List<LessonResponseDto> lessonDtos = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDtos.add(LessonResponseDto.from(lesson));
        }
        return lessonDtos;
    }



}
