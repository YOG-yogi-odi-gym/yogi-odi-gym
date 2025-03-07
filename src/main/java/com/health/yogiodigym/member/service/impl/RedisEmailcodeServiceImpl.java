package com.health.yogiodigym.member.service.impl;

import com.health.yogiodigym.common.exception.EmailcodeNotFoundException;
import com.health.yogiodigym.member.service.RedisEmailcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisEmailcodeServiceImpl implements RedisEmailcodeService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setCode(String email,String code){
        ValueOperations<String, String> valOperations = redisTemplate.opsForValue();

        valOperations.set(email,code,300, TimeUnit.SECONDS);
    }

    public String getCode(String email) {
        ValueOperations<String, String> valOperations = redisTemplate.opsForValue();
        String code = valOperations.get(email);
        if (code == null) {
            throw new EmailcodeNotFoundException();
        }
        return code;
    }
}
