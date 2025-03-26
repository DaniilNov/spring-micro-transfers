package ru.otus.java.pro.mt.core.transfers.factories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;

@Configuration
public class RestClientFactory {

    @Bean
    public RestClient createRestClient(@Qualifier("restClientProperties") RestClientProperties properties) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(properties.getReadTimeout());
        requestFactory.setConnectTimeout(properties.getConnectTimeout());

        return RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(properties.getUrl())
                .build();
    }
}