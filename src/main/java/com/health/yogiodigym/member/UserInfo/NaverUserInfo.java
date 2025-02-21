package com.health.yogiodigym.member.UserInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;
    private Map<String, Object> response;

    public NaverUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
        this.response = (Map<String, Object>)attributes.get("response");
    }

    @Override
    public String getName() {
        return response.get("name").toString();
    }

    @Override
    public String getEmail() {
        return response.get("email").toString();
    }

    @Override
    public String getProfile() {
        return response.get("profile_image").toString();
    }
}
