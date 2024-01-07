package com.andver.testcontainers.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.andver.testcontainers.redis.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RedisContainerTest extends BaseRedisContainerTest {

  public RedisContainerTest(@Autowired CacheService cacheService) {
    super(cacheService);
  }

  @Test
  void shouldStart() {
    assertTrue(REDIS_CONTAINER.isRunning());
  }

  @Test
  void shouldWrite() {
    User testUser = new User("Andrei", 31);
    cacheService.save("test", testUser);
    assertEquals(testUser, cacheService.find("test"));
  }
}
