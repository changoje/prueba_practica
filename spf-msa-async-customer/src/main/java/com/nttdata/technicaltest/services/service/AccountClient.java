package com.nttdata.technicaltest.services.service;

import com.nttdata.technicaltest.services.client.account.models.PostCustomerAccountRequest;
import reactor.core.publisher.Mono;

public interface AccountClient {
    Mono<Void> postSaveAccount(PostCustomerAccountRequest postCustomerAccountRequest);
}
