package com.health.yogiodigym.member.service;

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
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String)kakaoAccount.get("email");

        Member kakaoLoginMember = memberRepository.findByEmail(email);
        if(kakaoLoginMember != null){
            return new MemberOAuth2User(kakaoLoginMember, kakaoAccount);
        }else{
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            Set<Role> newMemberRoles = EnumSet.of(Role.ROLE_USER);

            Member newKakaoMember = Member.builder()
                    .name((String)profile.get("nickname"))
                    .email((String)kakaoAccount.get("email"))
                    .profile((String)profile.get("profile_image_url"))
                    .roles(newMemberRoles)
                    .status("ACTIVE")
                    .build();

            return new MemberOAuth2User(memberRepository.save(newKakaoMember), kakaoAccount);
        }
    }
}