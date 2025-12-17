package com.petnote.api.auth.refresh;

import com.petnote.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;

@Log4j2
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final AuthService authService;

    public String sha256(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(String userId, String refreshToken){
        authService.setRefreshToken(userId, sha256(refreshToken));
    }

    public boolean validate(String userId, String provided){
        String saved = authService.getRefreshToken(userId);
        return saved != null && saved.equals(sha256(provided));
    }

    public void invalidate(String userId){
        authService.setRefreshToken(userId, null);
    }
}
