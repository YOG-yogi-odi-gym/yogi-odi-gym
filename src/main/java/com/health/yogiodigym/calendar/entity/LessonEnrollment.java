package com.health.yogiodigym.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson_enrollment")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, name="unread_message")
    private Integer unreadMessage = 0;
}
