package com.health.yogiodigym.admin.controller;


import com.health.yogiodigym.admin.dto.BoardDto.*;
import com.health.yogiodigym.admin.service.service.AdminBoardService;
import com.health.yogiodigym.common.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.health.yogiodigym.common.message.SuccessMessage.ADMIN_BOARD_SEARCH_SUCCESS;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardService adminBoardService;

    @GetMapping("/board/search")
    public ResponseEntity<?> adminSearchBoards(@RequestParam String boardKeyword) {
        List<BoardResponseDto> boards =adminBoardService.adminSearchBoards(boardKeyword);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK,ADMIN_BOARD_SEARCH_SUCCESS.getMessage(), boards));
    }

}
