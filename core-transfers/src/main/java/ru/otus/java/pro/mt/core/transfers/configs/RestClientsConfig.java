package ru.otus.java.pro.mt.core.transfers.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;
import ru.otus.java.pro.mt.core.transfers.factories.RestClientFactory;

@Configuration
public class RestClientsConfig {

    private final RestClientFactory restClientFactory;
    private final RestClientProperties restClientProperties;

    public RestClientsConfig(RestClientFactory restClientFactory, RestClientProperties restClientProperties) {
        this.restClientFactory = restClientFactory;
        this.restClientProperties = restClientProperties;
    }

    @Bean
    @ConditionalOnMissingBean(RestClient.class)
    public RestClient limitsClient() {
        return restClientFactory.createRestClient(restClientProperties);
    }
}
