package com.health.yogiodigym.lesson.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.entity.Lesson;
import com.health.yogiodigym.lesson.entity.LessonEnrollment;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LessonEnrollmentRepositoryTest {

    @Autowired
    private LessonEnrollmentRepository lessonEnrollmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    @DisplayName("회원으로 수강 목록 조회")
    void testFindAllByMember() {
        // given
        Member member = Member.builder()
                .name("user1")
                .email("user1@gmail.com")
                .build();
        memberRepository.save(member);

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId("test-room")
                .isGroupChat(true)
                .build();
        chatRoomRepository.save(chatRoom);

        Category category = Category.builder()
                .name("category")
                .code("code")
                .build();
        categoryRepository.save(category);

        Lesson lesson1 = createMockLesson(category, member, chatRoom);
        Lesson lesson2 = createMockLesson(category, member, chatRoom);
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);

        LessonEnrollment lessonEnrollment1 = LessonEnrollment.builder()
                .member(member)
                .lesson(lesson1)
                .build();
        LessonEnrollment lessonEnrollment2 = LessonEnrollment.builder()
                .member(member)
                .lesson(lesson2)
                .build();
        lessonEnrollmentRepository.save(lessonEnrollment1);
        lessonEnrollmentRepository.save(lessonEnrollment2);

        // when
        List<LessonEnrollment> lessonEnrollments = lessonEnrollmentRepository.findAllByMember(member);

        // then
        assertThat(lessonEnrollments.size()).isEqualTo(2);
    }

    private Lesson createMockLesson(Category category, Member member, ChatRoom chatRoom) {
        return Lesson.builder()
                .title("title")
                .category(category)
                .days(1)
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .startDay(LocalDate.now())
                .endDay(LocalDate.now())
                .max(30)
                .master(member)
                .chatRoom(chatRoom)
                .build();
    }
}