package com.health.yogiodigym.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/images/source/**", "/css/**", "/js/**", "/", "/member/regist", "/member/find-pwd",
                                "/api/member/regist", "/api/member/send-code" ,"api/member/mail-verify", "/api/member/find-pwd").permitAll()
                        .requestMatchers("/images/license/**", "/api/admin/**", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/member/login", "/login").not().authenticated()
                        .requestMatchers("/logout", "/dashboard").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .defaultSuccessUrl("/dashboard", true)
                        .failureHandler((request, response, exception) -> {
                            log.error("로그인 실패: " + exception.getMessage(), exception);

                            String errorMessage;
                            if(exception instanceof BadCredentialsException) {
                                errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
                            }else{
                                errorMessage = exception.getMessage();
                            }

                            response.sendRedirect("/member/login?error="+ URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
                        })
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
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
                ).addFilterBefore(new IncompleteUserFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
