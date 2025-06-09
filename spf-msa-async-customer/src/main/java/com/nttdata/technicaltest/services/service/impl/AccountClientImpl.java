package com.nttdata.technicaltest.services.service.impl;

import com.nttdata.technicaltest.services.client.account.models.PostCustomerAccountRequest;
import com.nttdata.technicaltest.services.config.PropertiesConfiguration;
import com.nttdata.technicaltest.services.service.AccountClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountClientImpl implements AccountClient {
    private final PropertiesConfiguration propertiesConfiguration;

    @Override
    public Mono<Void> postSaveAccount(PostCustomerAccountRequest postCustomerAccountRequest) {
        WebClient client = WebClient.create();
        return client.post()
                .uri(propertiesConfiguration.getUri())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(postCustomerAccountRequest))
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
