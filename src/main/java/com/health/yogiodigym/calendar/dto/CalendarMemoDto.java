package com.health.yogiodigym.calendar.dto;

import com.health.yogiodigym.calendar.entity.CalendarMemo;
import lombok.*;

import java.time.LocalDate;

public class CalendarMemoDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarMemoSelectDto {

        private Long id;
        private String title;
        private String context;
        private LocalDate date;
        private Long memberId;

        public CalendarMemoSelectDto(CalendarMemo memo) {
            this.id = memo.getId();
            this.title = memo.getTitle();
            this.context = memo.getContext();
            this.date = memo.getDate();
            this.memberId = memo.getMember().getId();
        }

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarMemoInsertDto {

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
    public static class CalendarMemoUpdateDto {

        private Long id;
        private String title;
        private String context;
        private LocalDate date;
        private Long memberId;
    }

}
