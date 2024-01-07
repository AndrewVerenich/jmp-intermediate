package com.andver.testcontainers.redis.model;

import java.io.Serializable;

public record User(String name, Integer age) implements Serializable {

}
