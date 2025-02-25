package com.health.yogiodigym.member.config;

import com.health.yogiodigym.member.auth.MemberStatus;
import com.health.yogiodigym.member.entity.MemberOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class IncompleteUserFilter extends OncePerRequestFilter {
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/member/regist", "/member/login", "/login", "/api/member/regist",
            "/images/", "/css/", "/js/"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (EXCLUDED_PATHS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MemberOAuth2User user) {
            if (user.getMember().getStatus() == MemberStatus.INCOMPLETE) {
                response.sendRedirect("/member/regist");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
