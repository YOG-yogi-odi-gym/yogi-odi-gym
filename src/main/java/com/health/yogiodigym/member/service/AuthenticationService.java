package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.entity.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MemberDetailsService memberDetailsService;

    public void updateAuthentication(String email) {
        MemberOAuth2User updatePrincipal = (MemberOAuth2User) memberDetailsService.loadUserByUsername(email);

        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
                updatePrincipal,
                updatePrincipal.getPassword(),
                updatePrincipal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
    }
}
