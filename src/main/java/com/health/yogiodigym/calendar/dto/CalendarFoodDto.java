package com.health.yogiodigym.calendar.dto;

import lombok.*;

import java.time.LocalDate;

public class CalendarFoodDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectRequest {

        private Long id;
        private String name;
        private Float hundredGram;
        private Float calories;
        private LocalDate date;
        private Long memberId;

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InsertRequest {

        private String name;
        private Float hundredGram;
        private Float calories;
        private LocalDate date;
        private Long memberId;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {

        private Long id;
        private String name;
        private Float hundredGram;
        private Float calories;
        private LocalDate date;
        private Long memberId;

    }


}
