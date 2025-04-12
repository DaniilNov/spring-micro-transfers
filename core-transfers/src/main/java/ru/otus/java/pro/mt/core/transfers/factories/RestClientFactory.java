package ru.otus.java.pro.mt.core.transfers.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RestClientFactory {

    private final Map<String, RestClient> cachedClients;

    public RestClientFactory(@Qualifier("restClientProperties") RestClientProperties properties) {
        this.cachedClients = properties.getClients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> createRestClient(entry.getValue())
                ));

        cachedClients.forEach((name, client) -> log.info("Created RestClient for {}: {}",
                name, formatClientProperties(properties.getClients().get(name))));
    }

    public RestClient getClient(String name) {
        RestClient client = cachedClients.get(name);
        if (client == null) {
            throw new IllegalArgumentException("Client with name '" + name + "' is not configured");
        }
        return client;
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