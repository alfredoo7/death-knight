package com.fishshell.dk.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishshell.dk.service.model.StpOptionSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @author alfred.zhou
 * @since 2019-01-08
 */
@Configuration
@AllArgsConstructor
public class StpOptionSourceConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public StpOptionSource source() throws Exception {
        InputStream resourceAsStream = StpOptionSourceConfig.class.getResourceAsStream("/stpOptionSource.json");
        StpOptionSource source = objectMapper.readValue(resourceAsStream, StpOptionSource.class);
        return source;
    }
}
