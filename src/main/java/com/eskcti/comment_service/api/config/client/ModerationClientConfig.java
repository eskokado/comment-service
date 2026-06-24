package com.eskcti.comment_service.api.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.eskcti.comment_service.api.client.ModerationClient;
import com.eskcti.comment_service.api.client.RestClientFactory;

@Configuration
public class ModerationClientConfig {

    @Bean
    public ModerationClient moderationClient(RestClientFactory factory) {
        RestClient restClient = factory.moderationRestClient();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
            .builderFor(adapter)
            .build();

        return proxyFactory.createClient(ModerationClient.class);
    }
}
