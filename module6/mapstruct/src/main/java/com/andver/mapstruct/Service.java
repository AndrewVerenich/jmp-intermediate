package com.andver.mapstruct;

import com.andver.mapstruct.mapper.SimpleSourceDestinationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class Service {

    private final SimpleSourceDestinationMapper mapper;

    public void executeCall() {
        log.info(mapper.toString());
    }
}
