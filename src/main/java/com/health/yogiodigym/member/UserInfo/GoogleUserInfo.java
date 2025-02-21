package com.health.yogiodigym.member.UserInfo;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GoogleUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getProfile() {
        return attributes.get("picture").toString();
    }
}
