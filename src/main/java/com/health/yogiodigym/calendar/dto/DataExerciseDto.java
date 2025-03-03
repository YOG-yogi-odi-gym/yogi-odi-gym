package com.health.yogiodigym.calendar.dto;


import lombok.*;


public class DataExerciseDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectRequest {

        private Long id;
        private String name;
        private Float energyConsumption;
    }
}
