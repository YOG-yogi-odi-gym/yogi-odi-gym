package com.health.yogiodigym.member.config;

import com.health.yogiodigym.member.service.KakaoOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final KakaoOAuth2UserService kakaoOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/", "/member/login", "/member/register").not().authenticated()
                        .requestMatchers("/logout").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/loginPro")
                        .permitAll()
                        .defaultSuccessUrl("/dashboard", true)
                )
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo
                        .userService(kakaoOAuth2UserService)))
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/member/login")
                        .permitAll()
                );
        return http.build();
    }

}
