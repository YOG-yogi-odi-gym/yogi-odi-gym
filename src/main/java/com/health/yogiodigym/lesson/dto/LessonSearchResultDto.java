package com.health.yogiodigym.lesson.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class LessonSearchResultDto {
    private List<LessonDto> content;
    private int totalPages;

    public LessonSearchResultDto(List<LessonDto> content, int totalPages) {
        this.content = content;
        this.totalPages = totalPages;
    }

    public List<LessonDto> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }
}