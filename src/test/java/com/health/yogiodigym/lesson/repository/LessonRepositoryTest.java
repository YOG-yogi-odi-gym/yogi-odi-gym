package com.health.yogiodigym.lesson.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.health.yogiodigym.chat.entity.ChatRoom;
import com.health.yogiodigym.chat.repository.ChatRoomRepository;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.lesson.entity.Lesson;
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
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    @DisplayName("회원으로 강의 목록 조회")
    void testFindAllByMember() {
        // given
        Member member = Member.builder()
                .email("email@email.com")
                .name("member")
                .build();
        memberRepository.save(member);

        Category category = Category.builder()
                .name("축구")
                .code("code")
                .build();
        categoryRepository.save(category);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .roomId("test-room-1")
                .build();
        ChatRoom chatRoom2 = ChatRoom.builder()
                .roomId("test-room-2")
                .build();
        chatRoomRepository.save(chatRoom1);
        chatRoomRepository.save(chatRoom2);

        Lesson lesson1 = createMockLesson(category, member, chatRoom1);
        Lesson lesson2 = createMockLesson(category, member, chatRoom2);
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);

        // when
        List<Lesson> lessons = lessonRepository.findAllByMaster(member);

        // then
        assertThat(lessons.size()).isEqualTo(2);
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