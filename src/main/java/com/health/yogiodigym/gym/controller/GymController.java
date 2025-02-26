package com.health.yogiodigym.gym.controller;

import com.health.yogiodigym.common.response.HttpResponse;
import com.health.yogiodigym.gym.dto.DataGymDto;
import com.health.yogiodigym.gym.entity.DataGym;
import com.health.yogiodigym.gym.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.health.yogiodigym.common.message.SuccessMessage.SEARCH_GYMS_SUCCESS;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @GetMapping("/search")
    public ResponseEntity<?> searchGyms(@RequestParam(required = false) String gymKeyword,
                                        @RequestParam(defaultValue = "name") String searchColumn,
                                        @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<DataGymDto> gyms = gymService.findByGymSearch(gymKeyword, pageable, searchColumn);

        return ResponseEntity.ok().body(new HttpResponse(HttpStatus.OK, SEARCH_GYMS_SUCCESS.getMessage(), gyms));
    }
}