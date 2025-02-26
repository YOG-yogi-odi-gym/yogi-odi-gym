package com.health.yogiodigym.member.UserInfo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private static final String ATTRIBUTES_NAME = "kakao_account";
    private static final String PROFILE = "profile";
    private static final String NAME = "nickname";
    private static final String EMAIL = "email";
    private static final String IMAGE = "profile_image_url";

    private Map<String, Object> attributes;
    private Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get(ATTRIBUTES_NAME);
        this.profile = (Map<String, Object>) this.attributes.get(PROFILE);
    }

    @Override
    public String getName() {
        return profile.get(NAME).toString();
    }

    @Override
    public String getEmail() {
        return attributes.get(EMAIL).toString();
    }

    @Override
    public String getProfile() {
        return profile.get(IMAGE).toString();
    }
}
