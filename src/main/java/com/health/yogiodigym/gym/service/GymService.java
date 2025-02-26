package com.health.yogiodigym.gym.service;

import com.health.yogiodigym.gym.dto.DataGymDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Objects;

public interface GymService {

    Page<DataGymDto> findByGymSearch(String gymKeyword, Pageable pageable, String searchColumn);
}