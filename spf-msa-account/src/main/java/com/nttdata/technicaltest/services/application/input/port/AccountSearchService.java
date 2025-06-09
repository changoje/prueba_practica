package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.model.GetCustomerAccountResponse;
import reactor.core.publisher.Mono;

public interface AccountSearchService {
    Mono<GetCustomerAccountResponse> getCustomerAccount(ParametersBuilder parametersBuilder);
}