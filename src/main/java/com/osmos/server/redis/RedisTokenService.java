package com.osmos.server.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setToken(String userId, String token) {
        redisTemplate.opsForValue().set(userId, token);
    }

    public UUID getUserId(String token) {
        return UUID.fromString(redisTemplate.opsForValue().get(token));
    }

    public String getTokenByUserId(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

}
