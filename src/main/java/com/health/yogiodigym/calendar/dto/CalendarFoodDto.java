package com.health.yogiodigym.calendar.dto;

import com.health.yogiodigym.calendar.entity.CalendarFood;
import lombok.*;

import java.time.LocalDate;

public class CalendarFoodDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarFoodSelectDto {

        private Long id;
        private String name;
        private Float hundredGram;
        private Float calories;
        private LocalDate date;
        private Long memberId;
        private Long foodId;
        private Integer cal;

        public CalendarFoodSelectDto(CalendarFood food) {
            this.id = food.getId();
            this.name = food.getName();
            this.hundredGram = food.getHundredGram();
            this.calories = food.getCalories();
            this.date = food.getDate();
            this.memberId = food.getMember().getId();
            this.foodId = food.getDataFood().getId();
            this.cal = food.getDataFood().getCalories();
        }
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarFoodInsertDto {

        private String name;
        private Float hundredGram;
        private Float calories;
        private LocalDate date;
        private Long memberId;
        private Long foodId;

    }

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarFoodUpdateDto {

        private Long id;
        private String name;
        private Float hundredGram;
        private Float calories;
        private LocalDate date;
        private Long memberId;
        private Long foodId;
    }

}
