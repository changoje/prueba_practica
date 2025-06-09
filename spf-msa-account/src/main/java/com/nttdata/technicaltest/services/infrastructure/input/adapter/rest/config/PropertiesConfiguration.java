package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Getter
@Setter
@ToString
public class PropertiesConfiguration {
    @Value("${backend.time.session}")
    private String timeSession;
}
