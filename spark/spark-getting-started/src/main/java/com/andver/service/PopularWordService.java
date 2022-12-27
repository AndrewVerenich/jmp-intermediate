package com.andver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PopularWordService
{
    private final JavaSparkContext sparkContext;

    @PostConstruct
    public void findPopularWord()
    {
        String input = "java java groovy java groovy scala";
        JavaRDD<String> rdd = sparkContext.parallelize(Arrays.asList(input));

        List<String> take = rdd.map(String::toLowerCase)
                .flatMap(inputString -> Arrays.stream(inputString.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b)
                .mapToPair(Tuple2::swap)
                .sortByKey(false)
                .map(Tuple2::_2)
                .take(3);

        log.info(take.toString());
    }
}
