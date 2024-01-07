package com.andver.testcontainers;

import com.andver.testcontainers.redis.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class TestContainersApp {

  public static void main(String[] args) {
    SpringApplication.run(TestContainersApp.class);
  }

  @Bean
  public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, User> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
    return template;
  }
}