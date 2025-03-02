package com.health.yogiodigym.admin.dto;

import com.health.yogiodigym.board.entity.Board;
import lombok.*;

public class BoardDto {

    @Setter
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardResponseDto {
        private Long id;
        private String title;
        private String memberName;

        public static BoardResponseDto from(Board board) {
            return BoardResponseDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .memberName(board.getMember().getName())
                    .build();
        }
    }
}
