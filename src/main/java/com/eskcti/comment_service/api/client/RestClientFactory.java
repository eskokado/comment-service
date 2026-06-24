package com.eskcti.comment_service.api.client;

import java.time.Duration;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder builder;

    public RestClient moderationRestClient() {
        return builder
            .baseUrl("http://localhost:8084")
            .requestFactory(createHttpRequestFactory())
            .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                throw new ModerationClientBadGatewayException(
                    "Failed to communicate with the Moderation service");
            })
            .build();
    }

    private ClientHttpRequestFactory createHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(5));
        return factory;
    }
}
