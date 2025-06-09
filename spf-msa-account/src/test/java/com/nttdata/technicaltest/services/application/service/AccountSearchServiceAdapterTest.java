package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.model.GetCustomerAccountResponse;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountSearchServiceAdapterTest {

    @Mock
    AccountRepositoryPort accountEntityRepository;
    @Mock
    PersonCustomerService personCustomerService;

    @InjectMocks
    AccountSearchServiceAdapter accountSearchService;

    @Test
    void testGetCustomerAccount_Success() {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));

        when(accountEntityRepository.findByCustomerId(anyLong())).thenReturn(Flux.just(MockData.mapperToAccountEntity()));

        Mono<GetCustomerAccountResponse> result = accountSearchService.getCustomerAccount(BuilderObject.parametersBuilder("documentNumber", null, null, null));

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCustomer().getDocumentNumber().equals("2365897896"))
                .verifyComplete();
    }
}
