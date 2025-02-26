package com.health.yogiodigym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YogiOdiGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(YogiOdiGymApplication.class, args);
    }

}
