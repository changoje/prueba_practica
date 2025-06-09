package com.nttdata.technicaltest.services.aplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.aplication.input.port.PersonCustomerService;
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

@ExtendWith(MockitoExtension.class)
class CustomerUpdateServiceAdapterTest {

    @InjectMocks
    CustomerUpdateServiceAdapter customerUpdateService;

    @Mock
    PersonCustomerService personCustomerService;
    @Mock
    TransactionalOperator transactionalOperator;

    @BeforeEach
    public void setUp() {
        Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void putCustomerUpdate() throws JsonProcessingException {

        Mockito.when(personCustomerService.findPersonByDocumentNumber(Mockito.anyString())).thenReturn(Mono.just(MockData.personDto()));
        Mockito.when(personCustomerService.savePerson(Mockito.any())).thenReturn(Mono.just(MockData.entityPerson()));
        Mockito.when(personCustomerService.findCustomerByPersonId(Mockito.anyLong())).thenReturn(Mono.just(MockData.customerDto()));
        Mockito.when(personCustomerService.updateCustomer(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());

        StepVerifier
                .create(customerUpdateService.putCustomer("1716456764", MockData.buildPutCustomerUpdateRequest()))
                .expectNextCount(0)
                .verifyComplete();


        Mockito.verify(personCustomerService, Mockito.times(1)).findPersonByDocumentNumber(Mockito.anyString());
        Mockito.verify(personCustomerService, Mockito.times(1)).savePerson(Mockito.any());
        Mockito.verify(personCustomerService, Mockito.times(1)).findCustomerByPersonId(Mockito.anyLong());
        Mockito.verify(personCustomerService, Mockito.times(1)).updateCustomer(Mockito.any(), Mockito.any());
    }
}