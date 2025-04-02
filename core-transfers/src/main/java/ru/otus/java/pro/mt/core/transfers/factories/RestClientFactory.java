package ru.otus.java.pro.mt.core.transfers.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class RestClientFactory {

    private final RestClientProperties properties;

    public RestClientFactory(@Qualifier("restClientProperties") RestClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Map<String, RestClient> restClients() {
        Map<String, RestClient> clients = properties.getClients().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> createRestClient(entry.getValue())));

        clients.forEach((name, client) -> log.info("Created RestClient for {}: {}", name,
                formatClientProperties(properties.getClients().get(name))));
        return clients;
    }

    private RestClient createRestClient(RestClientProperties.ClientProperties properties) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout((int) properties.getReadTimeout().toMillis());
        requestFactory.setConnectTimeout((int) properties.getConnectTimeout().toMillis());

        return RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(properties.getUrl())
                .build();
    }

    /**
     * Для демонстрации параметров созданных RestClient
     */
    private String formatClientProperties(RestClientProperties.ClientProperties clientProperties) {
        return String.format("url: %s, readTimeout: %s, connectTimeout: %s",
                clientProperties.getUrl(),
                clientProperties.getReadTimeout().getSeconds(),
                clientProperties.getConnectTimeout().getSeconds());
    }
}