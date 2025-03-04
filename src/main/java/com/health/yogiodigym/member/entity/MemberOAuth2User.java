package com.health.yogiodigym.member.entity;

import com.health.yogiodigym.member.status.MemberStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.health.yogiodigym.member.status.MemberStatus.*;

@Setter
@Getter
public class MemberOAuth2User implements UserDetails, OAuth2User {

    private Member member;
    private final Map<String, Object> attributes;

    public MemberOAuth2User(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    public MemberOAuth2User(Member member) {
        this.member = member;
        this.attributes = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return member.getPwd();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        MemberStatus status = member.getStatus();
        if (status == SUSPENDED) {
            throw new LockedException(member.getDropDate()+"까지 정지된 계정입니다.");
        }else if(status == BAN){
            throw new LockedException("영구정지 계정입니다.");
        }

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        MemberStatus status = member.getStatus();
        if(status == INACTIVE){
            throw new DisabledException("탈퇴대기 계정입니다. "+member.getDropDate().plusDays(3)+"부터 다시 회원가입이 가능합니다");
        }else if(status != ACTIVE){
            throw new DisabledException("사용하실 수 없는 계정입니다.");
        }
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return member.getEmail();
    }
}
