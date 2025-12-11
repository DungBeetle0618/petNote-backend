package com.petnote.api.auth.jwt;

import com.petnote.api.auth.user.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;
    private final RequestMatcher permitAllMatcher;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return permitAllMatcher.matches(request);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("====================jwtFiler 진입==========================");
        log.info("====================requestURI : {}", request.getRequestURI());
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims body = jwtProvider.parseToken(token).getBody();
                log.info("====================parseToken==========================");
                log.info("body = {}", body);
                String username = body.getSubject();

                UserDetails userDetail = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e){
                log.info("======================catch=======================");
                /*
                    유효하지 않으면 통과 -> 컨트롤러에서 403 처리
                 */
            }
        }
        filterChain.doFilter(request, response);

    }
}
