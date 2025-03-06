package com.health.yogiodigym.board.dto;

import com.health.yogiodigym.board.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(max = 500, message = "댓글 내용은 500자를 초과할 수 없습니다.")
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