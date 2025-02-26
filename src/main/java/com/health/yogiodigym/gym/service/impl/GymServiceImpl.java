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
        Page<DataGym> gymPage = gymKeyword.isEmpty() ? gymRepository.findAll(pageable)
                : determineSearchMethod(gymKeyword, pageable, searchColumn);
        return gymPage.map(DataGymDto::new);
    }

    private Page<DataGym> determineSearchMethod(String gymKeyword, Pageable pageable, String searchColumn) {
        return Objects.equals(searchColumn, "oldAddress")
                ? gymRepository.findByOldAddressContaining(gymKeyword, pageable)
                : gymRepository.findByNameContaining(gymKeyword, pageable);
    }
}