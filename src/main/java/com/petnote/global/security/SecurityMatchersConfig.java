package com.petnote.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityMatchersConfig {
    @Bean
    public RequestMatcher permitAllMatcher() {
        return new OrRequestMatcher(
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/auth/**"),
            new AntPathRequestMatcher("/oauth2/**"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/error"),
            new AntPathRequestMatcher("/api/**")
        );
    }

}
