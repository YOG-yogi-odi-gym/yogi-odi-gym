package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.LessonDto.*;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminLessonServiceImpl implements AdminLessonService {

    private final LessonRepository lessonRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponseDto> getAllLessons() {

        return lessonRepository.findAll().stream().map(LessonResponseDto::from).collect(Collectors.toList());
    }

    public List<LessonResponseDto> adminSearchLessons(String lessonKeyword) {

        return lessonRepository.adminSearchLessons(lessonKeyword).stream().map(LessonResponseDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        lessonRepository.deleteAllById(ids);
    }



}
