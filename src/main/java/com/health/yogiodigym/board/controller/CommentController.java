package com.health.yogiodigym.board.controller;

import com.health.yogiodigym.board.dto.CommentDto;
import com.health.yogiodigym.board.service.CommentService;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public List<CommentDto> getComments(@PathVariable Long boardId) {
        return commentService.getCommentsByBoardId(boardId);
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestParam Long boardId,
                                           @RequestParam String content,
                                           @AuthenticationPrincipal MemberOAuth2User loginUser) {
        commentService.addComment(boardId, loginUser.getMember().getId(), content);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal MemberOAuth2User loginUser) {
        commentService.deleteComment(commentId, loginUser.getMember().getId());
        return ResponseEntity.ok().build();
    }
}
