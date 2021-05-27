package com.epam.cassandra.dao;

import com.epam.cassandra.dto.Log;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LogDAO {

    private CassandraTemplate cassandraTemplate;

    public void save(Log log) {
        cassandraTemplate.insert(log);
    }

}
