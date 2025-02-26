package com.health.yogiodigym.lesson.entity;

import com.health.yogiodigym.lesson.dto.LessonDto;
import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int days;
    private String location;
    private float latitude;
    private float longitude;
    private String detailedLocation;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDay;
    private LocalDate endDay;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer current;
    private Integer max;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    private Member master;

    private LocalDateTime createDateTime;

    public void incrementCurrent() {
        if (this.current < this.max) {
            this.current++;
        } else {
            throw new IllegalStateException("수강 인원이 이미 최대입니다.");
        }
    }

    public void decrementCurrent() {
        if (this.current > 0) {
            this.current--;
        }
    }

    public void updateLesson(LessonDto.Edit dto, Category category) {
        this.title = dto.getTitle();
        this.days = dto.getDays();
        this.category = category;
        this.location = dto.getLocation();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.detailedLocation = dto.getDetailedLocation();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.startDay = dto.getStartDay();
        this.endDay = dto.getEndDay();
        this.max = dto.getMax();
        this.description = dto.getDescription();
    }

}