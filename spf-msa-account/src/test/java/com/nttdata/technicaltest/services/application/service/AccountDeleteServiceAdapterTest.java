package com.nttdata.technicaltest.services.application.service;

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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDeleteServiceAdapterTest {

    @Mock
    PersonCustomerService personCustomerService;

    @Mock
    AccountRepositoryPort accountEntityRepository;

    @InjectMocks
    AccountDeleteServiceAdapter accountDeleteService;

    private final String documentNumber = "123456789";
    private final String accountType = "SAVINGS";

    @Test
    void testDeleteCustomerAccountSuccess() {
        when(personCustomerService.getCustomer(documentNumber)).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.deleteByCustomerIdAndAccountType(12L, accountType)).thenReturn(Mono.empty());
        StepVerifier.create(accountDeleteService.deleteCustomerAccount(parametersBuilder()))
                .verifyComplete();

        verify(personCustomerService, times(1)).getCustomer(documentNumber);
    }

    @Test
    void testDeleteCustomerAccountErrorInRepository() {
        when(personCustomerService.getCustomer(documentNumber)).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.deleteByCustomerIdAndAccountType(12L, accountType)).thenReturn(Mono.empty());
        when(accountEntityRepository.deleteByCustomerIdAndAccountType(12L, accountType))
                .thenReturn(Mono.error(new RuntimeException("Database Error")));

        StepVerifier.create(accountDeleteService.deleteCustomerAccount(parametersBuilder()))
                .expectError(RuntimeException.class)
                .verify();

        verify(personCustomerService, times(1)).getCustomer(documentNumber);
        verify(accountEntityRepository, times(1)).deleteByCustomerIdAndAccountType(12L, accountType);
    }

    private ParametersBuilder parametersBuilder() {
        return BuilderObject.parametersBuilder(documentNumber, accountType, null, null);
    }
}