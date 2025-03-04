package com.health.yogiodigym.board.dto;

import com.health.yogiodigym.board.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long boardId;
    private String content;
    private LocalDateTime createDateTime;
    private boolean edit;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getId();
        this.memberName = comment.getMember().getName();
        this.boardId = comment.getBoard().getId();
        this.content = comment.getContent();
        this.createDateTime = comment.getCreateDateTime();
        this.edit = comment.isEdit();
    }
}