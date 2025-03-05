package com.health.yogiodigym.board.dto;

import com.health.yogiodigym.board.entity.Board;
import com.health.yogiodigym.board.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long id;
    private Long memberId;
    private Long categoryId;
    private String title;
    private String context;
    private LocalDateTime createDateTime;
    private int view;
    private boolean edit;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.memberId = board.getMember().getId();
        this.categoryId = board.getCategory().getId();
        this.title = board.getTitle();
        this.context = board.getContext();
        this.createDateTime = board.getCreateDateTime();
        this.view = board.getView();
        this.edit = board.isEdit();
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardDetailDto{
        private Long id;
        private Long memberId;
        private String memberName;
        private Long categoryId;
        private String categoryName;
        private String title;
        private String context;
        private LocalDateTime createDateTime;
        private int view;
        private boolean edit;
        private int commentCount;

        public BoardDetailDto(Board board) {
            this.id = board.getId();
            this.memberId = board.getMember().getId();
            this.memberName = board.getMember().getName();
            this.categoryId = board.getCategory().getId();
            this.categoryName = board.getCategory().getName();
            this.title = board.getTitle();
            this.context = board.getContext();
            this.createDateTime = board.getCreateDateTime();
            this.view = board.getView();
            this.edit = board.isEdit();
        }

        public String getFormattedCreateDateTime() {
            if (this.createDateTime == null) {
                return "등록일 없음";  // null 처리
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return this.createDateTime.format(formatter);
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardRequestDto{
        private Long memberId;
        private Long categoryId;
        private String title;
        private String context;
        private LocalDateTime createDateTime;
        private int view;
        private boolean edit;
    }
}