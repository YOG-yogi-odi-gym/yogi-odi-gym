package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.UserInfo.GoogleUserInfo;
import com.health.yogiodigym.member.UserInfo.KakaoUserInfo;
import com.health.yogiodigym.member.UserInfo.NaverUserInfo;
import com.health.yogiodigym.member.UserInfo.OAuth2UserInfo;
import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.auth.Role;
import com.health.yogiodigym.member.entity.Member;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import com.health.yogiodigym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class oAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(attributes);
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo(attributes);
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(attributes);
        }

        Member OAuthloginMember = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        if(OAuthloginMember != null){
            return new MemberOAuth2User(OAuthloginMember, attributes);
        }else{
            Member newOAuthMember = Member.builder()
                    .name(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .profile(oAuth2UserInfo.getProfile())
                    .roles(EnumSet.of(Role.ROLE_USER))
                    .status(MemberStatus.INACTIVE)
                    .build();

            log.info("\n\n\n\n\n\n"+newOAuthMember.getStatus()+"\n\n\n\n\n");
            return new MemberOAuth2User(newOAuthMember, attributes);
        }
    }
}