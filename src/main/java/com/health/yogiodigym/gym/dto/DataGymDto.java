package com.health.yogiodigym.gym.dto;

import com.health.yogiodigym.gym.entity.DataGym;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataGymDto {

    private Long id;
    private String name;
    private String oldAddress;
    private String streetAddress;
    private float latitude;
    private float longitude;
    private String phoneNum;
    private Integer totalArea;
    private Integer trainers;
    private LocalDate approvalDate;

    public DataGymDto(DataGym gym) {
        this.id = gym.getId();
        this.name = gym.getName();
        this.oldAddress = gym.getOldAddress();
        this.streetAddress = gym.getStreetAddress();
        this.latitude = gym.getLatitude();
        this.longitude = gym.getLongitude();
        this.phoneNum = gym.getPhoneNum();
        this.totalArea = gym.getTotalArea();
        this.trainers = gym.getTrainers();
        this.approvalDate = gym.getApprovalDate();
    }
}