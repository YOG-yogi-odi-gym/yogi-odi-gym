package com.health.yogiodigym.calendar.dto;

import lombok.*;

import java.time.LocalDate;

public class CalendarMemoDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectRequest {

        private Long id;
        private String title;
        private String context;
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

        private String title;
        private String context;
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
        private String title;
        private String context;
        private LocalDate date;
        private Long memberId;
    }

}
