package com.andver.configuration;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
    @Bean
    public JavaSparkContext sparkContext()
    {
        SparkConf sparkConf = new SparkConf()
                .setAppName("getting started")
                .setMaster("local[2]");

        return new JavaSparkContext(sparkConf);
    }
}
