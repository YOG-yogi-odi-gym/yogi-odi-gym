package com.health.yogiodigym.member.UserInfo;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private static final String ATTRIBUTES_NAME = "response";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String IMAGE = "profile_image";

    private final Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get(ATTRIBUTES_NAME);
    }

    @Override
    public String getName() {
        return attributes.get(NAME).toString();
    }

    @Override
    public String getEmail() {
        return attributes.get(EMAIL).toString();
    }

    @Override
    public String getProfile() {
        return attributes.get(IMAGE).toString();
    }
}
