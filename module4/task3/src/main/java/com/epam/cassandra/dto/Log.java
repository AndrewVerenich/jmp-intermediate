package com.epam.cassandra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("logs")
public class Log {
    @PrimaryKey
    private Integer processId;
    private LocalDateTime dateTime;
    private LogLevel logLevel;
    private String clazz;
    private String message;
}
