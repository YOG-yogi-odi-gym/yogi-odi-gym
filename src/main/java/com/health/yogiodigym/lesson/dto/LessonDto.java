package com.health.yogiodigym.lesson.dto;

import com.health.yogiodigym.lesson.entity.Lesson;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private Long id;
    private String title;
    private String categoryName;
    private Long masterId;
    private String masterName;
    private int days;
    private String location;
    private float latitude;
    private float longitude;
    private String startTime;
    private String endTime;
    private Integer current;
    private Integer max;

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.categoryName = lesson.getCategory().getName();
        this.masterId = lesson.getMaster().getId();
        this.masterName = lesson.getMaster().getName();
        this.days = lesson.getDays();
        this.location = lesson.getLocation();
        this.latitude = lesson.getLatitude();
        this.longitude = lesson.getLongitude();
        this.startTime = lesson.getStartTime().toString();
        this.endTime = lesson.getEndTime().toString();
        this.current = lesson.getCurrent();
        this.max = lesson.getMax();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LessonSearchDto {
        private Long id;
        private String title;
        private String categoryName;
        private Long masterId;
        private String masterName;
        private int days;
        private String location;
        private float latitude;
        private float longitude;
        private String startTime;
        private String endTime;
        private Integer current;
        private Integer max;

        public LessonSearchDto(Lesson lesson) {
            this.id = lesson.getId();
            this.title = lesson.getTitle();
            this.categoryName = lesson.getCategory().getName();
            this.masterId = lesson.getMaster().getId();
            this.masterName = lesson.getMaster().getName();
            this.days = lesson.getDays();
            this.location = lesson.getLocation();
            this.latitude = lesson.getLatitude();
            this.longitude = lesson.getLongitude();
            this.startTime = lesson.getStartTime().toString();
            this.endTime = lesson.getEndTime().toString();
            this.current = lesson.getCurrent();
            this.max = lesson.getMax();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LessonDetailDto {
        private Long id;
        private String title;
        private Long categoryId;
        private String categoryName;
        private int days;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate startDay;
        private LocalDate endDay;
        private String location;
        private float latitude;
        private float longitude;
        private String detailedLocation;
        private Long masterId;
        private String masterName;
        private String description;
        private Integer current;
        private Integer max;

        public LessonDetailDto(Lesson lesson) {
            this.id = lesson.getId();
            this.title = lesson.getTitle();
            this.categoryId = lesson.getCategory().getId();
            this.categoryName = lesson.getCategory().getName();
            this.days = lesson.getDays();
            this.startTime = lesson.getStartTime();
            this.endTime = lesson.getEndTime();
            this.startDay = lesson.getStartDay();
            this.endDay = lesson.getEndDay();
            this.location = lesson.getLocation();
            this.latitude = lesson.getLatitude();
            this.longitude = lesson.getLongitude();
            this.detailedLocation = lesson.getDetailedLocation();
            this.masterId = lesson.getMaster().getId();
            this.masterName = lesson.getMaster().getName();
            this.description = lesson.getDescription();
            this.current = lesson.getCurrent();
            this.max = lesson.getMax();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LessonEditDto {
        private Long id;
        private String title;
        private Long categoryId;
        private int[] bitDays;
        private String location;
        private String detailedLocation;
        private float latitude;
        private float longitude;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate startDay;
        private LocalDate endDay;
        private Integer max;
        private String description;

        public int getDays() {
            int bitmask = 0;
            for (int days : bitDays) {
                bitmask |= days;
            }
            return bitmask;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LessonEnrollmentDto {
        private Long memberId;
        private Long lessonId;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LessonRequestDto {
        private String title;
        private Long categoryId;
        private int[] bitDays;
        private String location;
        private String detailedLocation;
        private float latitude;
        private float longitude;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate startDay;
        private LocalDate endDay;
        private Integer max;
        private String description;

        public int getDays() {
            int bitmask = 0;
            for (int days : bitDays) {
                bitmask |= days;
            }
            return bitmask;
        }
    }
}
