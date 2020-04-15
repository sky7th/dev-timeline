package com.sky7th.devtimeline.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.REGEX;

@EnableBatchProcessing
@Configuration
@EnableAutoConfiguration
//@ComponentScan(excludeFilters = @Filter(type = REGEX, pattern = "com.sky7th.dev-timeline.batch.job.*"))
public class TestJobConfiguration {

}