package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.application.input.port.FacadeTransactionsService;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {AccountControllerTest.class})
class AccountControllerTest {

    @Mock
    private FacadeTransactionsService facadeTransactionsService;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private ServerWebExchange exchange;

    @Test
    void testPostCustomerAccount() throws JsonProcessingException {
        when(facadeTransactionsService.postCustomerAccount(any())).thenReturn(Mono.empty());
        Mono<ResponseEntity<Void>> response = accountController.postCustomerAccount(Mono.just(MockData.PostCustomerAccountRequest()), exchange);

        StepVerifier.create(response)
                .expectComplete()
                .verify();
    }

    @Test
    void testPostCustomerAccountSuccess() throws JsonProcessingException {
        when(facadeTransactionsService.postCustomerAccount(any())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> response = accountController.postCustomerAccount(Mono.just(MockData.PostCustomerAccountRequest()), exchange);

        StepVerifier.create(response)
                .expectComplete()
                .verify();

        verify(facadeTransactionsService, times(1)).postCustomerAccount(any());
    }

    @Test
    void testGetCustomerAccount() throws JsonProcessingException {
        when(facadeTransactionsService.getCustomerAccount(any()))
                .thenReturn(Mono.just(MockData.getCustomerAccountResponse()));

        Mono<ResponseEntity<GetCustomerAccountResponse>> response = accountController.getCustomerAccount("0788100072", exchange);

        StepVerifier.create(response)
                .assertNext(getCustomerAccountResponseResponseEntity ->
                        assertThat(getCustomerAccountResponseResponseEntity.getStatusCode(),
                                equalTo(HttpStatus.OK)))
                .verifyComplete();
    }

    @Test
    void testDeleteCustomerAccount() {
        when(facadeTransactionsService.deleteCustomerAccount(Mockito.any()))
                .thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> response = accountController.deleteCustomerAccount("123", "savings", exchange);

        StepVerifier.create(response)
                .expectComplete()
                .verify();
    }

    @Test
    void testPatchCustomerAccount() {
        when(facadeTransactionsService.patchCustomerAccount(any(), any()))
                .thenReturn(Mono.empty());

        Mono<PatchCustomerAccountRequest> request = Mono.just(new PatchCustomerAccountRequest());
        Mono<ResponseEntity<Void>> response = accountController.patchCustomerAccount("123", "savings", request, exchange);

        StepVerifier.create(response)
                .expectComplete()
                .verify();
    }

    @Test
    void testPostCustomerTransactions() {
        when(facadeTransactionsService.postCustomerTransactions(any()))
                .thenReturn(Mono.empty());

        Mono<PostCustomerTransactionsRequest> request = Mono.just(new PostCustomerTransactionsRequest());
        Mono<ResponseEntity<Void>> response = accountController.postCustomerTransactions(request, exchange);

        StepVerifier.create(response)
                .expectComplete()
                .verify();
    }

    @Test
    void testGetCustomerTransaction() {
        Flux<CustomerTransactions> transactionsFlux = Flux.just(new CustomerTransactions());
        when(facadeTransactionsService.getCustomerTransaction(Mockito.any()))
                .thenReturn(transactionsFlux);

        Mono<ResponseEntity<Flux<CustomerTransactions>>> response = accountController.getCustomerTransaction("123", "2023-01-01", "2023-01-31", "savings", exchange);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.OK && entity.getBody() != null)
                .verifyComplete();
    }

}