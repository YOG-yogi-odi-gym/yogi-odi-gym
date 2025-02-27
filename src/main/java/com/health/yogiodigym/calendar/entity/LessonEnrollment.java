//package com.health.yogiodigym.calendar.entity;
//
//import com.health.yogiodigym.member.entity.Member;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@ToString
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class LessonEnrollment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    //@ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne
//    @JoinColumn(name = "lesson_id", nullable = false)
//    private Lesson lesson;
//
//    //@ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;
//
//    @Column(nullable = false)
//    private Integer unreadMessage = 0;
//}
