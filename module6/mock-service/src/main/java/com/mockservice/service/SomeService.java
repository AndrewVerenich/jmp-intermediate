package com.mockservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    private Logger logger = LoggerFactory.getLogger(SomeService.class);

    public void someMethod(){
        logger.info("Log from service");
    }
}
