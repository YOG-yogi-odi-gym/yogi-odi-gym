package com.health.yogiodigym.admin.service.impl;

import com.health.yogiodigym.admin.dto.LessonDto;
import com.health.yogiodigym.admin.service.service.AdminLessonService;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminLessonServiceImpl implements AdminLessonService {

    private final LessonRepository lessonRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LessonDto.LessonResponseDto> getAllLessons() {

        List<Lesson> lessons = lessonRepository.findAll();

        List<LessonDto.LessonResponseDto> lessonDtos = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDtos.add(LessonDto.LessonResponseDto.from(lesson));
        }

//      return lessons.stream().map(l -> LessonDto.LessonResponseDto.from(l)).collect(Collectors.toList());

        return lessonDtos;
    }

}
