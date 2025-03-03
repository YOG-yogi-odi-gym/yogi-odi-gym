package com.health.yogiodigym.calendar.dto;

import lombok.*;

public class DataFoodDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectRequest {

        private Long id;
        private String name;
        private Integer calories;
    }
}
