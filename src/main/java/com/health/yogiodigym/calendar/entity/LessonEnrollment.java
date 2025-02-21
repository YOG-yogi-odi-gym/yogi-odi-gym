package com.health.yogiodigym.calendar.entity;

import com.health.yogiodigym.calendar.entity.Lesson;
import com.health.yogiodigym.calendar.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Lesson_Enrollment")
@Getter
@Setter
@NoArgsConstructor
public class LessonEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 수강 번호 (기본키)

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson; // 강의 번호 (외래키)

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 수강생 아이디 (회원 참조)

    @Column(nullable = false, name="unread_message")
    private Integer unreadMessage = 0; // 읽지 않은 채팅 수 (기본값 0)
}
