package com.nttdata.technicaltest.services.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.AccountException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.application.service.factory.FactoryTransactionServiceAdapter;
import com.nttdata.technicaltest.services.application.input.port.Operation;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransactionsServiceAdapterTest {

    @Mock
    PersonCustomerService personCustomerService;

    @Mock
    AccountRepositoryPort accountEntityRepository;

    @Mock
    FactoryTransactionServiceAdapter configOperation;

    @Mock
    TransactionalOperator transactionalOperator;

    @InjectMocks
    AccountTransactionsServiceAdapter accountTransactionsService;

    @BeforeEach
    void setup() {
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void postCustomerTransactions_successfulTransaction() throws JsonProcessingException {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        Operation operation = Mockito.mock(Operation.class);
        when(operation.execute(any(), anyDouble(), any())).thenReturn(Mono.empty());
        when(accountEntityRepository.findByAccountNumber(anyString()))
                .thenReturn(Mono.just(MockData.mapperToAccountEntity()));
        when(configOperation.getOperation(any())).thenReturn(operation);

        Mono<Void> result = accountTransactionsService.postCustomerTransactions(MockData.PostPostCustomerTransactionsRequest());

        StepVerifier.create(result)
                .verifyComplete();

        verify(accountEntityRepository).findByAccountNumber(anyString());
        verify(configOperation).getOperation(any());
        verify(operation).execute(any(), anyDouble(), any());
    }

    @Test
    void postCustomerTransactions_accountNotFound_inDatabase() throws JsonProcessingException {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByAccountNumber(anyString()))
                .thenReturn(Mono.empty());

        Mono<Void> result = accountTransactionsService.postCustomerTransactions(MockData.PostPostCustomerTransactionsRequest());

        StepVerifier.create(result)
                .expectError(AccountException.class)
                .verify();

        verify(accountEntityRepository).findByAccountNumber(anyString());
    }

    @Test
    void postCustomerTransactions_accountNotAllowed() throws JsonProcessingException {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByAccountNumber(anyString()))
                .thenReturn(Mono.just(MockData.accountDisabled()));

        Mono<Void> result = accountTransactionsService.postCustomerTransactions(MockData.PostPostCustomerTransactionsRequest());

        StepVerifier.create(result)
                .expectError(AccountException.class)
                .verify();

        verify(accountEntityRepository).findByAccountNumber(anyString());
    }
}