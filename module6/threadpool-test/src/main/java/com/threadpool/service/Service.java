package com.threadpool.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class Service {

    private ExecutorService executorService;

    public void runTask() {
        RestTemplate restTemplate = new RestTemplate();
        while (true) {
            executorService.submit(() -> {
                        long before = System.currentTimeMillis();
                        restTemplate.exchange("http://localhost:1233/endpoint", HttpMethod.GET, null, String.class);
                        System.out.println(System.currentTimeMillis() - before);
                    }
            );
        }
    }
}
