package com.andver.httpclient;

import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class Service
{
    RestTemplate restTemplate;

    @Retryable(value = RuntimeException.class, maxAttempts = 5)
    public String executeCall()
    {
        return restTemplate.getForObject("http://www.google123.com", String.class);
    }
}
