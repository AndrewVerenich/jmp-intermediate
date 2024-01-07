package com.andver.testcontainers.redis;

import com.andver.testcontainers.redis.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

  private final ValueOperations<String, User> valueOps;

  public CacheService(RedisTemplate<String, User> template) {
    this.valueOps = template.opsForValue();
  }

  public void save(String key, User user) {
    valueOps.set(key, user);
  }

  public User find(String key) {
    return valueOps.get(key);
  }
}
