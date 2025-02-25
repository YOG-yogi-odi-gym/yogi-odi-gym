package com.health.yogiodigym.gym.service;

import com.health.yogiodigym.gym.entity.DataGym;
import com.health.yogiodigym.gym.repository.GymRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GymService {

    private final GymRepository gymRepository;

    public GymService(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    public Page<DataGym> findByGymSearch(String gymKeyword, int page, int size, String searchColumn){
        Pageable pageable = PageRequest.of(page, size);
        if(!gymKeyword.isEmpty()){
            if (Objects.equals(searchColumn, "oldAddress")){
                return gymRepository.findByOldAddressContaining(gymKeyword, pageable);
            }else {
                return gymRepository.findByNameContaining(gymKeyword, pageable);
            }
        }else {
            return gymRepository.findAll(pageable);
        }
    }
}