package com.petnote.api.auth.refresh;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final StringRedisTemplate redis;
    private static final String PREFIX = "refresh:";

    private String sha256(String s){
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

    public void save(String userId, String deviceId, String refreshToken, Duration duration){
        redis.opsForValue().set(PREFIX + userId + ":" + deviceId, sha256(refreshToken), duration);
    }

    public boolean validate(String userId, String deviceId, String provided){
        String saved = redis.opsForValue().get(PREFIX + userId + ":" + deviceId);
        return saved != null && saved.equals(sha256(provided));
    }

    public void invalidate(String userId, String deviceId){
        redis.delete(PREFIX + userId + ":" + deviceId);
    }
}
