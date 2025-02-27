package com.health.yogiodigym.member.UserInfo;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String IMAGE = "picture";

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
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
