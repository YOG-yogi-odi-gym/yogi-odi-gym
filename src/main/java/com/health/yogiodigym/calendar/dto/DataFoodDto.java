package com.health.yogiodigym.calendar.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataFoodDto {

     private Long id;
     private String name;
     private Integer calories;
}
