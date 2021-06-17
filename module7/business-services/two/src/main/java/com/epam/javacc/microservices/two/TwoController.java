package com.epam.javacc.microservices.two;

import com.netflix.servo.annotations.DataSourceLevel;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@RestController("twoController")
public class TwoController {

    @Monitor(name = "requestCounter", type = DataSourceType.COUNTER, description = "Total number of requests", level = DataSourceLevel.INFO)
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @Monitor(name = "aGauge", type = DataSourceType.GAUGE, description = "A random gauge", level = DataSourceLevel.CRITICAL)
    private final AtomicInteger aGauge = new AtomicInteger(0);

    @MonitorTags
    private final TagList tags = BasicTagList.of("id", "twoController", "class", "com.epam.javacc.microservices.two.TwoController");

    @PostConstruct
    public void init() {
        Monitors.registerObject("twoController", this);
    }

    @GetMapping
    public String helloTwo() {
        requestCounter.incrementAndGet();
        aGauge.set(RandomUtils.nextInt(100));
        return "Hello from service Two";
    }
}
