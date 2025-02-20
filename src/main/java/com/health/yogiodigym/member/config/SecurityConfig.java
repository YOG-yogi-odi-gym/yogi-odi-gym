package com.health.yogiodigym.member.config;

import com.health.yogiodigym.member.service.KakaoOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final KakaoOAuth2UserService kakaoOAuth2UserService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KakaoAuthorizationRequestResolver kakaoAuthorizationRequestResolver() {
        return new KakaoAuthorizationRequestResolver(clientRegistrationRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/images/**", "/css/**", "/js/**", "/").permitAll()
                        .requestMatchers("/member/login", "/member/join", "/login").not().authenticated()
                        .requestMatchers("/logout", "/dashboard").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .defaultSuccessUrl("/dashboard", true)
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .authorizationEndpoint(authorization ->
                                authorization.authorizationRequestResolver(kakaoAuthorizationRequestResolver())
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(kakaoOAuth2UserService)
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/member/login")
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/member/login");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/dashboard");
                        })
                );
        return http.build();
    }

}
