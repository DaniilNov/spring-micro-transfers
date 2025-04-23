package ru.otus.java.pro.mt.core.transfers.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.factories.RestClientFactory;

@Configuration
public class RestClientsConfig {

    private final RestClientFactory restClientFactory;

    public RestClientsConfig(RestClientFactory restClientFactory) {
        this.restClientFactory = restClientFactory;
    }

    @Bean
    @ConditionalOnMissingBean(name = "limitsRestClient")
    public RestClient limitsClient() {
        return restClientFactory.getClient("limits");
    }

    @Bean
    @ConditionalOnMissingBean(name = "paymentsRestClient")
    public RestClient paymentsClient() {
        return restClientFactory.getClient("payments");
    }
}
