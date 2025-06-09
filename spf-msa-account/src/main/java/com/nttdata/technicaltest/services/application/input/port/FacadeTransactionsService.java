package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FacadeTransactionsService {
    Mono<Void> postCustomerAccount(PostCustomerAccountRequest postCustomerAccountRequest);

    Mono<Void> deleteCustomerAccount(ParametersBuilder parametersBuilder);

    Mono<GetCustomerAccountResponse> getCustomerAccount(ParametersBuilder parametersBuilder);

    Mono<Void> patchCustomerAccount(ParametersBuilder parametersBuilder, PatchCustomerAccountRequest patchCustomerAccountRequest);

    Mono<Void> postCustomerTransactions(PostCustomerTransactionsRequest postCustomerTransactionsRequest);

    Flux<CustomerTransactions> getCustomerTransaction(ParametersBuilder parametersBuilder);
}
