package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.infrastructure.exception.DatabaseSavingOperationException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.BuilderObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransactionsSearchServiceAdapterTest {

    @Mock
    PersonCustomerService personCustomerService;
    @Mock
    AccountRepositoryPort accountEntityRepository;

    @InjectMocks
    AccountTransactionsSearchServiceAdapter service;


    @Test
    void getCustomerTransactionSuccess() {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByCustomerId(anyLong())).thenReturn(Flux.just(MockData.mapperToAccountEntity()));

        StepVerifier.create(service.getCustomerTransaction(parametersBuilder()))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getCustomerTransactionNoAccountsFound() {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByCustomerId(anyLong())).thenReturn(Flux.empty());
        StepVerifier.create(service.getCustomerTransaction(parametersBuilder()))
                .expectError(DatabaseSavingOperationException.class)
                .verify();
    }

    @Test
    void getCustomerTransactionNoTransactionsFound() {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByCustomerId(anyLong())).thenReturn(Flux.just(MockData.mapperToAccountEntity()));

        StepVerifier.create(service.getCustomerTransaction(parametersBuilder()))
                .expectNextMatches(customerTransactions -> customerTransactions.getAccounts().isEmpty())
                .verifyComplete();
    }

    private ParametersBuilder parametersBuilder() {
        return BuilderObject.parametersBuilder("docNumber", "SAVINGS", "2023-01-01T00:00:00", "2023-12-31T23:59:59");
    }
}