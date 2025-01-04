package com.example.democontroller;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rest-clients")
public record DynamicRestBuilderProperties(List<CustomClient> clients) {
    public record CustomClient(String clientName, int connectionTimeout, int responseTimeout, String userAgent) {
    }
}
