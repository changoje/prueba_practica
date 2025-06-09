package com.nttdata.technicaltest.services.application.service.facade;

import com.nttdata.technicaltest.services.application.input.port.*;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class FacadeTransactionsServiceAdapter implements FacadeTransactionsService {

    private final AccountCreationService accountCreationService;

    private final AccountDeleteService accountDeleteService;
    private final AccountSearchService accountSearchService;
    private final AccountUpdateService accountUpdateService;
    private final AccountTransactionsService accountTransactionsService;
    private final AccountTransactionsSearchService accountTransactionsSearchService;

    @Override
    public Mono<Void> postCustomerAccount(PostCustomerAccountRequest postCustomerAccountRequest) {
        return accountCreationService.postCustomerAccount(postCustomerAccountRequest);
    }

    @Override
    public Mono<Void> deleteCustomerAccount(ParametersBuilder parametersBuilder) {
        return accountDeleteService.deleteCustomerAccount(parametersBuilder);
    }

    @Override
    public Mono<GetCustomerAccountResponse> getCustomerAccount(ParametersBuilder parametersBuilder) {
        return accountSearchService.getCustomerAccount(parametersBuilder);
    }

    @Override
    public Mono<Void> patchCustomerAccount(ParametersBuilder parametersBuilder, PatchCustomerAccountRequest patchCustomerAccountRequest) {
        return accountUpdateService.patchCustomerAccount(parametersBuilder, patchCustomerAccountRequest);
    }

    @Override
    public Mono<Void> postCustomerTransactions(PostCustomerTransactionsRequest postCustomerTransactionsRequest) {
        return accountTransactionsService.postCustomerTransactions(postCustomerTransactionsRequest);
    }

    @Override
    public Flux<CustomerTransactions> getCustomerTransaction(ParametersBuilder parametersBuilder) {
        return accountTransactionsSearchService.getCustomerTransaction(parametersBuilder);
    }
}
