package com.health.yogiodigym.calendar.dto;

import com.health.yogiodigym.calendar.entity.CalendarExercise;
import lombok.*;

import java.time.LocalDate;

public class CalendarExerciseDto {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarExerciseSelectDto {

        private Long id;
        private String name;
        private Float time;
        private Float calories;
        private LocalDate date;
        private Long memberId;
        private Long exerciseId;
        private Float energyConsumption;

        public CalendarExerciseSelectDto(CalendarExercise exercise) {
            this.id = exercise.getId();
            this.name = exercise.getDataExercise().getName();
            this.time = exercise.getTime();
            this.calories = exercise.getCalories();
            this.date = exercise.getDate();
            this.memberId = exercise.getMember().getId();
            this.exerciseId = exercise.getDataExercise().getId();
            this.energyConsumption = exercise.getDataExercise().getEnergyConsumption();
        }

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarExerciseInsertDto {

        private String name;
        private Float time;
        private Float calories;
        private LocalDate date;
        private Long memberId;
        private Long exerciseId;

    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalendarExerciseUpdateDto {

        private String name;
        private Long id;
        private Float time;
        private Float calories;
        private LocalDate date;
        private Long memberId;
        private Long exerciseId;

    }
}
