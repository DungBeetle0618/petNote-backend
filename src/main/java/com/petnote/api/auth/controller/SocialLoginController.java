package com.petnote.api.auth.controller;


import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.jwt.JwtProvider;
import com.petnote.api.auth.refresh.RefreshTokenService;
import com.petnote.api.auth.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth/social")
@Log4j2
@RequiredArgsConstructor
public class SocialLoginController {
    private final SocialLoginService socialLoginService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${app.jwt.refresh-exp-days}")
    private int REFRESH_TOKEN_EXPIRE_DAY;


    @PostMapping("/kakao/login")
    public ResponseEntity<AuthController.TokenRes> kakaoLogin(@RequestBody LoginDTO loginRequest) throws Exception {
        log.info("login accessT : {}", loginRequest.getAccessToken());
        LoginDTO loginDTO = socialLoginService.kakaoLogin(loginRequest.getAccessToken());
        String access = jwtProvider.generateAccessToken(loginDTO.getUserId(), Map.of("role", "USER"));
        String refresh = jwtProvider.generateRefreshToken(access);
        //TODO 운영 전환 시 redis 환경 설정 후 주석 풀기, yaml redis 주석 해제
        //refreshTokenService.save(userId, req.deviceId == null ? "device" : req.deviceId, refresh, Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY));

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refresh)
                //TODO 운영 시 secure -> true
                .httpOnly(true).secure(false)
                .sameSite("Lax")
                .path("/auth")
                .maxAge(Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthController.TokenRes(access));
    }

    @PostMapping("/naver/login")
    public ResponseEntity<AuthController.TokenRes> naverLogin(@RequestBody LoginDTO loginRequest) throws Exception {
        log.info("login accessT : {}", loginRequest.getAccessToken());

        LoginDTO loginDTO = socialLoginService.naverLogin(loginRequest.getAccessToken());
        String access = jwtProvider.generateAccessToken(loginDTO.getUserId(), Map.of("role", "USER"));
        String refresh = jwtProvider.generateRefreshToken(access);
        //TODO 운영 전환 시 redis 환경 설정 후 주석 풀기, yaml redis 주석 해제
        //refreshTokenService.save(userId, req.deviceId == null ? "device" : req.deviceId, refresh, Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY));

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refresh)
                //TODO 운영 시 secure -> true
                .httpOnly(true).secure(false)
                .sameSite("Lax")
                .path("/auth")
                .maxAge(Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthController.TokenRes(access));
    }
}
