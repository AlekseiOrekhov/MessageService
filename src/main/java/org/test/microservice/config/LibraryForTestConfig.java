package org.test.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.test.library.CalculateService;
import org.test.library.CalculateServiceImpl;
import org.test.library.MetricService;
import org.test.library.MetricServiceImpl;

@Configuration
public class LibraryForTestConfig {
    @Bean
    public MetricService MertricServiceImpl() {
        return new MetricServiceImpl();
    }

    @Bean
    public CalculateService CalculateServiceImpl() { return new CalculateServiceImpl(); }
}
