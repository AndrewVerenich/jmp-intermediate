package com.epam.javacc.microservices.common;

import com.netflix.config.DynamicPropertyFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint to retrieve dynamic properties from config.properties file
 */
@RestController
public class ConfigPropertiesController {


    private DynamicPropertyFactory propertiesFactory = DynamicPropertyFactory.getInstance();

    @GetMapping("/property/{propertyName}")
    public String getProperty(@PathVariable String propertyName) {
        return propertiesFactory
                .getStringProperty(propertyName, "not found!")
                .get();
    }
}
