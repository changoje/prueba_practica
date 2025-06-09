package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import reactor.core.publisher.Mono;

public interface AccountDeleteService {
    Mono<Void> deleteCustomerAccount(ParametersBuilder parametersBuilder);
}