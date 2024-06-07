package com.v.hana.service;

import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired private RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public void right_push(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public List<Object> right_pop_all(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void deleteArray(String key) {
        redisTemplate.delete(key);
    }

    public boolean checkExistsValue(String key) {
        return redisTemplate.hasKey(key);
    }
}
