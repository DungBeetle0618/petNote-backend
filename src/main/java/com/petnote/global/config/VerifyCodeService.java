package com.petnote.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class VerifyCodeService {
    private final StringRedisTemplate redis;
    private final AuthVerifyProperties props;

    public enum Purpose { FIND_ID, RESET_PW }

    private String codeKey(Purpose p, String email, String userId) {
        // userId는 RESET_PW에서만 사용
        if(p == Purpose.FIND_ID) {
            return "code:fid:" + email;
        }else if(p == Purpose.RESET_PW) {
            return "code:rpw:" + userId + ":" + email;
        }
        return null;
    }

    public String issueCode(Purpose purpose, String email, String userId) {
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
        String key = codeKey(purpose, email, userId);
        redis.opsForValue().set(key, code, Duration.ofMinutes(props.getCodeTtlMin()));
        return code;
    }

    public boolean verifyCode(Purpose purpose, String email, String userId, String input) {
        String key = codeKey(purpose, email, userId);
        String saved = redis.opsForValue().get(key);
        if (saved == null) return false;            // 만료 or 없음
        if (!saved.equals(input)) return false;     // 불일치
        redis.delete(key);                          // 1회성 사용
        return true;
    }
}