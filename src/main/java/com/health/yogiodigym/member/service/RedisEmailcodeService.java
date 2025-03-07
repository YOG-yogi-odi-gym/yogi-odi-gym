package com.health.yogiodigym.member.service;

public interface RedisEmailcodeService {

    void setCode(String email,String code);

    String getCode(String email);
}
