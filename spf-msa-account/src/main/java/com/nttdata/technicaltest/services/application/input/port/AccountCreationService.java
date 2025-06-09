package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.model.PostCustomerAccountRequest;
import reactor.core.publisher.Mono;

public interface AccountCreationService {
    Mono<Void> postCustomerAccount(PostCustomerAccountRequest postCustomerAccountRequest);
}
