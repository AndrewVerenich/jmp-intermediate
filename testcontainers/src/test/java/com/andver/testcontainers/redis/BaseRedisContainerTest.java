package com.andver.testcontainers.redis;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
abstract class BaseRedisContainerTest {

  @Container
  @ServiceConnection
  protected static final RedisContainer REDIS_CONTAINER =
      new RedisContainer(DockerImageName.parse("redis:6.2.6"));

  protected final CacheService cacheService;

  public BaseRedisContainerTest(CacheService cacheService) {
    this.cacheService = cacheService;
  }
}
