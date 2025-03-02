package com.health.yogiodigym.board.entity;

import com.health.yogiodigym.board.dto.BoardDto.*;
import com.health.yogiodigym.lesson.entity.Category;
import com.health.yogiodigym.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String context;

    private LocalDateTime createDateTime;
    private int view;
    private boolean edit;

    public void incrementView() {
        this.view++;
    }


    public void updateBoard(BoardDetailDto dto, Category category) {
        this.title = dto.getTitle();
        this.context = dto.getContext();
        this.category = category;
        this.edit = true;
    }

}