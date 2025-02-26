package com.health.yogiodigym.admin.dto;


import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.member.entity.Member;
import lombok.*;

public class LessonDto {

    @Setter
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LessonResponseDto {
        private Long id;
        private String title;
        private String masterName;

        public static LessonResponseDto from(Lesson lesson) {
            return LessonResponseDto.builder()
                    .id(lesson.getId())
                    .title(lesson.getTitle())
                    .masterName(lesson.getMaster().getName())
                    .build();
        }
    }
}
