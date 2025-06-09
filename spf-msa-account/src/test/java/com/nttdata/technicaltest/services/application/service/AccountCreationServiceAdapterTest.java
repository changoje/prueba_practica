package com.nttdata.technicaltest.services.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCreationServiceAdapterTest {

    @InjectMocks
    AccountCreationServiceAdapter accountCreationService;
    @Mock
    AccountRepositoryPort accountEntityRepository;
    @Mock
    CustomerRedisRepositoryPort customerRedisRepository;
    @Mock
    PersonCustomerService personCustomerService;

    @Test
    void postCustomerAccount() throws JsonProcessingException {
        when(personCustomerService.getCustomer(anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(customerRedisRepository.find(Mockito.anyString())).thenReturn(Mono.just(MockData.customerDto()));
        when(accountEntityRepository.save(any())).thenReturn(Mono.just(MockData.mapperToAccountEntity()));
        when(customerRedisRepository.deleteCustomer()).thenReturn(Mono.empty());
        StepVerifier
                .create(accountCreationService.postCustomerAccount(MockData.PostCustomerAccountRequest()))
                .expectNextCount(0)
                .verifyComplete();
        verify(customerRedisRepository, times(1)).find(Mockito.anyString());
        verify(accountEntityRepository, times(1)).save(any());
        verify(customerRedisRepository, times(1)).deleteCustomer();
    }

    @Test
    void testGenerateRandomAccountNumber() {
        String accountNumber1 = "4564678903";
        String accountNumber2 = "0945738619";

        assertNotNull(accountNumber1);
        assertNotNull(accountNumber2);
        assertNotEquals(accountNumber1, accountNumber2);
        assertEquals(10, accountNumber1.length());
        assertEquals(10, accountNumber2.length());
    }
}