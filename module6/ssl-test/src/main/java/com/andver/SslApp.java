package com.andver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SslApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(SslApp.class);
    }

    @GetMapping
    public String hello()
    {
        return "I am working with both HTTP and HTTPS";
    }
}
