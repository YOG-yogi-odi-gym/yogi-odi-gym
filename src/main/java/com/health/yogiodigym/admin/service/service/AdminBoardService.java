package com.health.yogiodigym.admin.service.service;

import com.health.yogiodigym.admin.dto.BoardDto.*;

import java.util.List;

public interface AdminBoardService {

    List<BoardResponseDto> getAllBoards();

    List<BoardResponseDto> adminSearchBoards(String boardKeyword);


}
