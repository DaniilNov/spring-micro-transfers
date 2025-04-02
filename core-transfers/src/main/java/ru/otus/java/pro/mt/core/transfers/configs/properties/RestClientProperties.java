package ru.otus.java.pro.mt.core.transfers.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "rest")
public class RestClientProperties {
    private Map<String, ClientProperties> clients;

    @Data
    public static class ClientProperties {
        private String url;
        private Duration readTimeout;
        private Duration connectTimeout;
    }
}