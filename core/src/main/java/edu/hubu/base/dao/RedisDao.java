package edu.hubu.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisDao {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value, int seconds) {
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
