package com.health.yogiodigym.board.service;

import com.health.yogiodigym.board.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsByBoardId(Long boardId);

    void addComment(Long boardId, Long memberId, String content);

    public void deleteComment(Long commentId, Long memberId);
}
