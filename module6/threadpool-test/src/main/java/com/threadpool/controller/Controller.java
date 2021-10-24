package com.threadpool.controller;

import com.threadpool.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Controller {

    private Service service;

    @GetMapping("/run")
    void run() {
        service.runTask();
    }
}
