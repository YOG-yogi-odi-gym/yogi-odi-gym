package com.health.yogiodigym.gym.service.impl;

import com.health.yogiodigym.gym.dto.DataGymDto;
import com.health.yogiodigym.gym.entity.DataGym;
import com.health.yogiodigym.gym.repository.GymRepository;
import com.health.yogiodigym.gym.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;

    @Override
    public Page<DataGymDto> findByGymSearch(String gymKeyword, Pageable pageable, String searchColumn) {
        Page<DataGym> gymPage = gymRepository.findByKeywordAndColumn(gymKeyword, searchColumn, pageable);
        return gymPage.map(DataGymDto::new);
    }
}