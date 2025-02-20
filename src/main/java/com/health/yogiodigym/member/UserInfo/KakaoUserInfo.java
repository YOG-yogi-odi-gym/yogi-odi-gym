package com.health.yogiodigym.member.UserInfo;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getProfile() {
        return "";
    }
}
