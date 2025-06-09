package com.nttdata.technicaltest.services.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUpdateServiceAdapterTest {

    @Mock
    private AccountRepositoryPort accountEntityRepository;

    @Mock
    PersonCustomerService personCustomerService;

    @InjectMocks
    private AccountUpdateServiceAdapter accountUpdateService;

    @Test
    void patchCustomerAccountSuccess() throws JsonProcessingException {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByCustomerId(anyLong())).thenReturn(Flux.just(MockData.mapperToAccountEntity()));

        Mono<Void> result = accountUpdateService.patchCustomerAccount(parametersBuilder(), MockData.PostPatchCustomerAccountRequest());

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void patchCustomerAccountType() throws JsonProcessingException {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.findByCustomerId(anyLong())).thenReturn(Flux.just(MockData.mapperToAccountEntity()));

        Mono<Void> result = accountUpdateService.patchCustomerAccount(parametersBuilder(), MockData.PostPatchCustomerAccountRequest());

        StepVerifier.create(result)
                .verifyComplete();
    }

    private ParametersBuilder parametersBuilder() {
        String documentNumber = "2365897896";
        String accountType = "SAVINGS";
        return BuilderObject.parametersBuilder(documentNumber, accountType, null, null);
    }
}