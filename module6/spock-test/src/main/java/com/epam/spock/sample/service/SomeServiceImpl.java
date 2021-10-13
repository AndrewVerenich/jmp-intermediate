package com.epam.spock.sample.service;

import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {

    public int calculate(int a, int b) {
        return a + b;
    }

    public boolean getBool(){
        return false;
    }

}
