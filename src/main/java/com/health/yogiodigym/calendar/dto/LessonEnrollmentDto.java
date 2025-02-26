package com.health.yogiodigym.calendar.dto;

import com.health.yogiodigym.calendar.entity.Lesson;
import com.health.yogiodigym.calendar.entity.Member;
import lombok.*;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LessonEnrollmentDto {

    private Long id;
    private Lesson lesson;
    private Member member;
    //private Long memberId;


}
