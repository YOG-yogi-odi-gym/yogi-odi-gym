package com.health.yogiodigym.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

public class CalendarFoodDto {

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
        private String selectedDate;
        private Long memberId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate requestedDate;

        public void setSelectedDate(String selectedDate) {
            this.selectedDate = selectedDate;
            this.requestedDate = LocalDate.parse(selectedDate);
        }

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {

        private Long id;
        private String name;
        private Float hundredGram;
        private Float calories;
        private String selectedDate;
        private Long memberId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate requestedDate;

        public void setSelectedDate(String selectedDate) {
            this.selectedDate = selectedDate;
            this.requestedDate = LocalDate.parse(selectedDate);
        }
    }


}
