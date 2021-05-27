package com.epam.cassandra;

import com.epam.cassandra.dao.LogDAO;
import com.epam.cassandra.dto.Log;
import com.epam.cassandra.dto.LogLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
class DbPopulation {

    private LogDAO logDAO;

    public void populate() {
        Stream.generate(this::generateLog).limit(10_000).forEach(logDAO::save);
    }

    private Log generateLog() {
        return Log.builder().processId(new Random().nextInt(10_000)).dateTime(LocalDateTime.now())
                .logLevel(getLogLevel()).clazz("com.epam.cassandra.LogsStoreApp")
                .message(getLogMessage()).build();
    }

    private String getLogMessage() {
        return RandomStringUtils.randomAlphabetic(40);
    }

    private LogLevel getLogLevel() {
        int pick = new Random().nextInt(LogLevel.values().length);
        return LogLevel.values()[pick];
    }
}
