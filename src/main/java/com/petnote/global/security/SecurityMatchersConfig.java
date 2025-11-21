package com.petnote.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityMatchersConfig {
    @Bean
    public RequestMatcher permitAllMatcher() {
        return new OrRequestMatcher(
            PathPatternRequestMatcher.withDefaults().matcher("/auth/**"),
            PathPatternRequestMatcher.withDefaults().matcher("/oauth2/**"),
            PathPatternRequestMatcher.withDefaults().matcher("/actuator/**"),
            PathPatternRequestMatcher.withDefaults().matcher("/error")
        );
    }

}
