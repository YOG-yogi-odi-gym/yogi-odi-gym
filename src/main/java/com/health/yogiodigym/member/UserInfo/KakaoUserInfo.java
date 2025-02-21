package com.health.yogiodigym.member.UserInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, Object>) kakaoAccount.get("profile");
    }

    @Override
    public String getName() {
        return profile.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getProfile() {
        return profile.get("profile_image_url").toString();
    }
}
